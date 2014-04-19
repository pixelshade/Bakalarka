<?php
class User_item_m extends MY_Model
{
	protected $_table_name = 'user_items';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval'
			), 
		'item_id' => array(
			'field' => 'item_id', 
			'label' => 'item_id', 
			'rules' => 'intval'
			),	
		'amount' => array(
			'field' => 'amount', 
			'label' => 'amount', 
			'rules' => 'intval'
			),		
		);

	public function get_new ()
	{
		$user_item = new stdClass();

		$user_item->char_id = '';
		$user_item->item_id = '';
		$user_item->amount = 0;


		return $user_item;
	}

}