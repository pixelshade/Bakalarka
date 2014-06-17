<?php
class Region_movement_m extends MY_Model
{
	protected $_table_name = 'region_movement';
	protected $_order_by = 'id desc';
	public $rules = array(
		'region_id' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
			), 
		'json_params' => array(
			'field' => 'info', 
			'label' => 'Info', 
			'rules' => 'trim|required'
			),		
		);


	public function get_new ()
	{
		$region_movement = new stdClass();

		$region_movement->region_id = NONE_ID;
		$region_movement->json_params = '';
		
		return $region_movement;
	}

	public function create($region_id, $json_params){
		$region_movement['region_id'] = $region_id;
		$region_movement['json_params'] = $json_params;

		$this->save($region_movement);

	}

	public function get_json_params_for_region_id($region_id){
		$result = $this->get_by('`region_id` = "'.$region_id.'"', TRUE);
		if(!empty($result)){
			return $result->json_params;
		}
		return "";
	}

	public function move_all(){		
		$movements = $this->get();
		var_dump($movements);		
		foreach ($movements as $move) {	
			var_dump($move);		
			echo "<hr>";
			$movement_arr = json_decode($move->json_params);
			$min = idate('i');
			$steps = count($movement_arr);
			$actual_step = $min % $steps;

			$this->move_region($movement_arr[$actual_step],$move->region_id);
		}

	}


	private function move_region($movement,$region_id){		
		var_dump($movement);
		if(isset($region_id) && isset($movement) && isset($movement['lat_start']) && isset($movement['lon_start']) && isset($movement['lat_end'])	&& isset($movement['lon_end'])){
			$this->region_m->change_location($movement['lat_start'],$movement['lon_start'],$movement['lat_end'],$movement['lon_end'],$region_id);
		}
	}

}