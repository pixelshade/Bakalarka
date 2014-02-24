<?php
class Quest_m extends MY_Model
{
	protected $_table_name = 'quests';
	protected $_order_by = 'name, id desc';
	public $rules = array(
		'code' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[50]|xss_clean|alpha_numeric|is_unique[quests.code]'
		), 
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
		), 
		'info' => array(
			'field' => 'info', 
			'label' => 'Info', 
			'rules' => 'trim|required'
		),
		'image' => array(
			'field' => 'image', 
			'label' => 'Image', 
			'rules' => 'trim|max_length[255]|xss_clean'
		), 
		'reward_id' => array(
			'field' => 'reward_id', 
			'label' => 'Reward_id', 
			'rules' => 'intval'
		), 
		'autostart' => array(
			'field' => 'autostart', 
			'label' => 'autostart', 
			'rules' => 'trim|max_length[255]|xss_clean'
		), 
		'region_id' => array(
			'field' => 'region_id', 
			'label' => 'region_id', 
			'rules' => 'intval'
		), 
		'required_completed_quest_id' => array(
			'field' => 'required_completed_quest_id', 
			'label' => 'required_completed_quest_id', 
			'rules' => 'intval'
		), 
		'duration' => array(
			'field' => 'duration', 
			'label' => 'duration', 
			'rules' => 'intval'
		), 
	
	);

	public function get_new ()
	{
		$quest = new stdClass();

		$quest->name = '';
		$quest->info = '';
		$quest->image = '';
		$quest->lat_start = '';
		$quest->lon_start = '';
		$quest->lat_end = '';
		$quest->lon_end = '';

		return $quest;
	}

}