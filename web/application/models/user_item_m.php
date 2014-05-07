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

	public function get_items_for_char($char_id = NULL){
		if($char_id != NULL){
			$this->db->select('*');
			$this->db->from('item_definitions');			
			$this->db->where('char_id', $char_id);	
			$this->db->join($this->_table_name, 'item_definitions.id = '.$this->_table_name.".item_id", 'left');
			$result = $this->db->get()->result();
			return $result;
		}
		return NULL;
	}

	public function has_char_item_amount($char_id = NULL, $item_id = NULL, $amount = NULL){
		if($char_id!=NULL && $item_id != NULL && $amount != NULL){
			$this->db->select('*');
			$this->db->from($this->_table_name);
			$this->db->where('`char_id` = "'.$char_id.'" AND `item_id` = "'.$item_id.'" AND `amount` >= "'.$amount.'"');
			$result = $this->db->get()->result();
			if(!empty($result)){
				return TRUE;				
			}
		}
		return FALSE;
	}


}