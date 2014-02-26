<?php
class Item_definition_m extends MY_Model
{
	protected $_table_name = 'item_definitions';
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
	);

	public function get_new ()
	{
		$item_definition = new stdClass();

		$item_definition->name = '';
		$item_definition->info = '';
		$item_definition->image = '';

		return $item_definition;
	}

	public function get_for_dropdown(){
		$empty = array('' => 'No item');
		$result = (array)$this->get_array();		
		$result = array_column($result, 'name', 'id');
		$result = array_merge($empty,$result);		
		return $result;
	}

}