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
		'amount' => array(
			'field' => 'amount', 
			'label' => 'amount', 
			'rules' => 'intval'
			),	
		);

	public function get_new ()
	{
		$user_attribute = new stdClass();

		$user_attribute->char_id = '';
		$user_attribute->attribute_id = '';
		$user_attribute->amount = 0;

		return $user_attribute;
	}

	public function get_attributes_for_char($char_id = NULL){
		if($char_id != NULL){
			$this->db->select('*');
			$this->db->from('attributes');			
			$this->db->where('char_id', $char_id);	
			$this->db->join($this->_table_name, 'attributes.id = '.$this->_table_name.".attribute_id");
			$result = $this->db->get()->result();
			return $result;
		}
		return NULL;
	}

	public function has_char_attribute_amount($char_id = NULL, $attribute_id = NULL, $amount = NULL){
		if($char_id!=NULL && $attribute_id != NULL && $amount != NULL){
			$this->db->select('*');
			$this->db->from($this->_table_name);
			$this->db->where('`char_id` = "'.$char_id.'" AND `attribute_id` = "'.$attribute_id.'" AND `amount` >= "'.$amount.'"');
			$result = $this->db->get()->result();
			if(!empty($result)){
				return TRUE;				
			}
		}
		return FALSE;
	}

}