<?php
class User_attribute_m extends MY_Model
{
	protected $_table_name = 'user_attributes';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval'
			), 
		'attribute_id' => array(
			'field' => 'attribute_id', 
			'label' => 'attribute_id', 
			'rules' => 'intval'
			),		
		);

	public function get_new ()
	{
		$user_attribute = new stdClass();

		$user_attribute->char_id = '';
		$user_attribute->attribute_id = '';

		return $user_attribute;
	}

}