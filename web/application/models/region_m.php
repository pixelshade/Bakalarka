<?php
class Region_m extends MY_Model
{
	protected $_table_name = 'regions';
	protected $_order_by = 'name, id desc';
	public $rules = array(
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
			), 
		'info' => array(
			'field' => 'info', 
			'label' => 'Info', 
			'rules' => 'trim|required'
			),
		'image' => array(
			'field' => 'image', 
			'label' => 'Image', 
			'rules' => 'trim|max_length[255]|xss_clean'
			), 
		'lat_start' => array(
			'field' => 'lat_start', 
			'label' => 'Latitude start', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lon_start' => array(
			'field' => 'lon_start', 
			'label' => 'Longtitude start', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lat_end' => array(
			'field' => 'lat_end', 
			'label' => 'Latitude end', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lon_end' => array(
			'field' => 'lon_end', 
			'label' => 'Longtitude end', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'movement' => array(
			'field' => 'movement',
			'label' => 'Movement',
			'rules' => 'trim'
			)
		);

	public function get_new ()
	{
		$region = new stdClass();

		$region->name = '';
		$region->info = '';
		$region->image = '';
		$region->lat_start = '';
		$region->lon_start = '';
		$region->lat_end = '';
		$region->lon_end = '';
		$region->movement = '';

		return $region;
	}

	public function create($name, $info, $image, $lat_start, $lon_start, $lat_end, $lon_end, $movement){
		$region['name'] = $name;
		$region['info'] = $info;
		$region['image'] = $image;
		$region['lat_start'] = $lat_start;
		$region['lon_start'] = $lon_start;
		$region['lat_end'] = $lat_end;
		$region['lon_end'] = $lon_end;
		$region['movement'] = $movement;
		$this->save($region);

	}


	public function change_location($lat_start,$lon_start,$lat_end,$lon_end, $region_id){
		$region['lat_start'] = $lat_start;
		$region['lon_start'] = $lon_start;
		$region['lat_end'] = $lat_end;
		$region['lon_end'] = $lon_end;
		$this->save($region,$region_id);
	}

	public function get_for_dropdown(){
		$empty = array(NONE_ID => 'No region');
		$result = $this->get_array();		
		$result = array_column($result, 'name', 'id');		
		$result = $empty + $result;				
		return $result;
	}

	public function get_by_latlon($lat = NULL, $lon = NULL){
		if($lat != NULL || $lon != NULL){
			$where = "(`lat_start`<='".$lat."') AND (`lon_start`<='".$lon."') AND (`lat_end`>='".$lat."') AND (`lon_end`>='".$lon."')";
			$this->db->where($where);
			return $this->get_array(NULL, false);
		} 
		return NULL;		
	}

	public function move_all(){		
		$regions = $this->get_by('LENGTH(movement) > "10"');
		foreach ($regions as $region) {	
			$movement_arr = json_decode($region->movement);
			$min = idate('i');
			$steps = count($movement_arr);
			if($steps>0){
				$actual_step = $min % $steps;				
				$this->move_region($movement_arr[$actual_step],$region->id);
			}
		}

	}

	private function move_region($movement,$region_id){				
		if(isset($region_id) && isset($movement) && isset($movement->lat_start) && isset($movement->lon_start) && isset($movement->lat_end)	&& isset($movement->lon_end)){
			$this->change_location($movement->lat_start,$movement->lon_start,$movement->lat_end,$movement->lon_end,$region_id);
		}
	}

}