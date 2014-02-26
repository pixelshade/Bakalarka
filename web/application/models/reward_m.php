<?php
class Reward_m extends MY_Model
{
	protected $_table_name = 'rewards';
	protected $_order_by = 'id desc';
	public $rules = array(
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
		), 
		'item_definition_id' => array(
			'field' => 'item_definition_id', 
			'label' => 'Item_definition_id', 
			'rules' => 'intval'
		), 
		'item_amount' => array(
			'field' => 'item_amount', 
			'label' => 'Item_amount', 
			'rules' => 'intval'
		),
		'attribute_id' => array(
			'field' => 'attribute_id', 
			'label' => 'Attribute_id', 
			'rules' => 'intval'
		), 
		'attribute_amount' => array(
			'field' => 'attribute_amount', 
			'label' => 'Attribute_amount', 
			'rules' => 'intval'
		), 
	);

	public function get_new ()
	{
		$reward = new stdClass();

		$reward->name = '';
		$reward->item_definition_id = '';
		$reward->item_amount = '';
		$reward->attribute_id = '';
		$reward->attribute_amount = '';

		return $reward;
	}

}