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

		return $region;
	}

	public function change_location($lat_start,$lon_start,$lat_end,$lon_end, $region_id){
		$region['lat_start'] = $lat_start;
		$region['lon_start'] = $lon_start;
		$region['lat_end'] = $lat_end;
		$region['lon_end'] = $lon_end;
		save($region,$region_id);
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

}