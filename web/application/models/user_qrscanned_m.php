<?php
class User_qrscanned_m extends MY_Model
{
	protected $_table_name = 'user_qrscanned';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval|required'
			), 
		'qrscanned' => array(
			'field' => 'qrscanned', 
			'label' => 'qrscanned', 
			'rules' => 'trim|required|xss_clean'
			),	
		);

	public function get_new ()
	{
		$user_qrscanned = new stdClass();

		$user_qrscanned->char_id = '';
		$user_qrscanned->qrscanned = '';	

		return $user_qrscanned;
	}

	public function is_qrscanned_accepted_for_char_id($qrscanned, $char_id){
		$qrscanned = $this->get_by("`qrscanned` = '".$qrscanned."' AND `char_id` = '".$char_id."'");
		return (!empty($qrscanned));
	}

	public function is_qrscanned_active_for_char_id($qrscanned, $char_id){
		$qrscanned = $this->get_by("`qrscanned` = '".$qrscanned."' AND `char_id` = '".$char_id."'");
		if(!empty($qrscanned) && ($qrscanned->completed == 0)) {
			return TRUE;
		} 		
		return FALSE;
	}

	public function is_qrscanned_completed_for_char_id($qrscanned, $char_id){		
		$qrscanned = $this->get_by("`qrscanned` = '".$qrscanned."' AND `char_id` = '".$char_id."'",TRUE);		
		if(!empty($qrscanned) && ($qrscanned->completed == 1)){
			return TRUE;
		} 		
		return FALSE;
	}


}