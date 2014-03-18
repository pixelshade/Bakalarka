<?php
 /**
 * Item instance
 */
 class Item_instance_m extends MY_Model
 {
 	protected $_table_name = 'item_instances';
 	protected $_order_by = 'id desc';
 	public $rules = array( 		
 		'item_definition_id' => array(
 			'field' => 'item_definition_id', 
 			'label' => 'Item_definition_id', 
 			'rules' => 'intval'
 			), 
 		'latitude' => array(
 			'field' => 'latitude', 
 			'label' => 'latitude', 
 			'rules' => 'trim|decimal[10]|xss_clean'
 			),
 		'longtitude' => array(
 			'field' => 'longtitude', 
 			'label' => 'longtitude', 
 			'rules' => 'trim|decimal[10]|xss_clean'
 			), 	
 		'amount' => array(
 			'field' => 'amount', 
 			'label' => 'Item_amount', 
 			'rules' => 'intval'
 			), 	 		
 		'added_by_user' => array(
 			'field' => 'amount', 
 			'label' => 'amount', 
 			'rules' => 'intval'					
 			), 		
 		'code' => array(
 			'field' => 'code', 
 			'label' => 'code', 
 			'rules' => 'trim|required|xss_clean|unique[item_instances.code]'
 			),		
 		);

public function get_new ()
{
	$item_definition = new stdClass();

	$item_definition->item_definition_id = NONE_ID;
	$item_definition->latitude = null;
	$item_definition->longtitude = null;
	$item_definition->amount = 0;
	$item_definition->added_by_user = NONE_ID;
	$item_definition->code = '';



	return $item_definition;
}

public function get_by_latlon($player_lat = NULL, $player_lon = NULL, $radius = 0.05){
		if($player_lat != NULL || $player_lon != NULL){
			$player_lat += $radius;
			$player_lon += $radius;
			$where = "(`latitude`<=$player_lat  ) AND (`longtitude`<=$player_lon) AND (`latitude`>=$player_lat) AND (`longtitude`>=$player_lon)";
			$this->db->where($where);
			return $this->get_array(NULL, false);
		} 
		return NULL;		
	}

}
