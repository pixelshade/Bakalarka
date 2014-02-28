<?php
class Attribute_m extends MY_Model
{
	protected $_table_name = 'attributes';
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
		$attribute = new stdClass();

		$attribute->name = '';
		$attribute->info = '';
		$attribute->image = '';

		return $attribute;
	}

	public function get_for_dropdown(){
		$empty = array('' => 'No item');
		$result = $this->get_array();		
		$result = array_column($result, 'name', 'id');
		$result = $empty + $result;		
		return $result;
	}

}