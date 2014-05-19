<?php
class User_quest_m extends MY_Model
{
	protected $_table_name = 'user_quests';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval|required'
			), 
		'quest_id' => array(
			'field' => 'quest_id', 
			'label' => 'quest_id', 
			'rules' => 'intval|required'
			),
		'time_accepted' => array(
			'field' => 'time_accepted', 
			'label' => 'Time when was quest accepted', 
			'rules' => 'trim|exact_length[19]|xss_clean'
		), 	
		'completed' => array(
			'field' => 'completed', 
			'label' => 'completed', 
			'rules' => 'intval'			
			)	
		);

	public function get_new ()
	{
		$user_quest = new stdClass();

		$user_quest->char_id = '';
		$user_quest->quest_id = '';
		$user_quest->time_accepted = date('Y-m-d H:i:s');
		$user_quest->completed = 0;

		return $user_quest;
	}

	public function is_quest_accepted_for_char_id($quest_id, $char_id){
		$quest = $this->get_by("`quest_id` = '".$quest_id."' AND `char_id` = '".$char_id."'");
		return (!empty($quest));
	}

	public function is_quest_active_for_char_id($quest_id, $char_id){
		$quest = $this->get_by("`quest_id` = '".$quest_id."' AND `char_id` = '".$char_id."'");
		if(!empty($quest) && ($quest->completed == 0)) {
			return TRUE;
		} 		
		return FALSE;
	}

	public function is_quest_completed_for_char_id($quest_id, $char_id){		
		$quest = $this->get_by("`quest_id` = '".$quest_id."' AND `char_id` = '".$char_id."'",TRUE);		
		if(!empty($quest) && ($quest->completed == 1)){
			return TRUE;
		} 		
		return FALSE;
	}

	 

	public function get_quests_for_char($char_id = NULL){
		if($char_id != NULL){
			$this->db->select('*');
			$this->db->from($this->_table_name);			
			$this->db->where('char_id', $char_id);	
			$this->db->join('quests', 'quests.id = '.$this->_table_name.".quest_id", 'left');
			$result = $this->db->get()->result();
			return $result;
		}
		return NULL;
	}

	public function delete_all_with_char_id($char_id){
		$this->db->where('char_id', $char_id);		
		$this->db->delete($this->_table_name);
	}


	public function delete_all_with_quest_id($quest_id){
		$this->db->where('quest_id', $quest_id);		
		$this->db->delete($this->_table_name);
	}


}