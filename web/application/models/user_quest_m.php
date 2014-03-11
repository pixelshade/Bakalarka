<?php
class User_quest_m extends MY_Model
{
	protected $_table_name = 'user_quests';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval'
			), 
		'quest_id' => array(
			'field' => 'quest_id', 
			'label' => 'quest_id', 
			'rules' => 'intval'
			),
		'started' => array(
			'field' => 'started', 
			'label' => 'Starting time', 
			'rules' => 'trim|required|exact_length[10]|xss_clean'
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
		$user_quest->started = '';
		$user_quest->completed = '';

		return $user_quest;
	}

}