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
			$this->db->from($this->_table_name);			
			$this->db->where('char_id', $char_id);	
			$this->db->join('item_definitions', 'item_definitions.id = '.$this->_table_name.".item_id");
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
			$result = $this->db->get()->row();
			if(!empty($result)){
				return TRUE;				
			}
		}
		return FALSE;
	}

	public function get_char_item_amount($char_id = NULL, $item_id = NULL){
		if($char_id!=NULL && $item_id != NULL){
			$this->db->select('amount');
			$this->db->from($this->_table_name);
			$this->db->where('`char_id` = "'.$char_id.'" AND `item_id` = "'.$item_id.'"');
			$result = $this->db->get()->row();
			if(!empty($result)){
				return $result->amount;
			}			
		}
		return 0;
	}


	public function give_item_amount_from_char_to_char($item_id = NULL, $amount = NULL, $giver_id = NULL, $receiver_id = NULL){
		if($item_id != NULL && $amount != NULL && $giver_id != NULL && $receiver_id != NULL){
			$has_enough = $this->has_char_item_amount($giver_id, $item_id, $amount);
			if($amount>0){
				if($has_enough){
					$this->give_item_amount_to_char($item_id, -$amount, $giver_id);
					$this->give_item_amount_to_char($item_id, $amount, $receiver_id);
					return TRUE;
				}
			}
		}
		return FALSE;
	}


	public function give_item_amount_to_char($item_id = NULL, $item_amount = NULL, $char_id = NULL){						
		if($item_id == NULL && $item_amount == NULL && $char_id == NULL){
			return FALSE;
		}		

		$user_item = $this->get_array_by('`char_id` = "'.$char_id.'" AND `item_id` = "'.$item_id.'"', TRUE);		
		if(!empty($user_item)){
			$new_amount = $user_item['amount'] + $item_amount;
			$id = $user_item['id'];
		} else {			
			$new_amount = $item_amount;
			$id = NULL;
		}
		
		$user_item['char_id'] = $char_id;
		$user_item['item_id'] = $item_id;
		$user_item['amount'] = $new_amount;

		if($new_amount <= 0){
			$this->delete($id);
		} else {
			$this->save($user_item,$id);
		}	
		return TRUE;
	}


}