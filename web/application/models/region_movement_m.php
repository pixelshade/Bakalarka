<?php
class Region_m extends MY_Model
{
	protected $_table_name = 'region_movements';
	protected $_order_by = 'name, id desc';
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

}