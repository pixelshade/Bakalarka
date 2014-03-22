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
		$reward->item_definition_id = -1;
		$reward->item_amount = 0;
		$reward->attribute_id = -1;
		$reward->attribute_amount = 0;

		return $reward;
	}

	public function get_for_dropdown(){
		$empty = array(NONE_ID => 'No reward');
		$result = $this->get_array();		
		$result = array_column($result, 'name', 'id');
		$result = $empty + $result;		
		return $result;
	}

	private function generateUniqueCode(){
 		$this->load->helper('string'); 		
 		$code = random_string('alnum', (config_item('qrcode_length') - 1));
 		$exists = $this->get_by('`code` = "'.$code.QR_REWARD.'"', TRUE);
 		if(!empty($exists)){
 			$code = $this->generateUniqueCode();
 		}  		
 		return $code.QR_REWARD; 		
 	}

 	public function save($data, $id = NULL){
 		if($id == NULL){
 			$data['code'] = $this->generateUniqueCode();
 		}	
 		parent::save($data, $id);
 	}

}