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
		'completion_requirement_type' => array(
			'field' => 'completion_requirement_type', 
			'label' => 'completion_requirement_type', 
			'rules' => 'intval'
			), 
		'completion_requirement' => array(
			'field' => 'completion_requirement', 
			'label' => 'completion_requirement', 
			'rules' => 'trim|required|xss_clean'
			), 
		);

// required_answer(text_input)/required_item(item_id)/required_quest(quest_id)/required_objective(objective_id)/required_attribute_value(attribute value)
public $completion_types = array(
    '0' => 'Input an Answer',    
    '1' => 'Item in inventory',
    '2' => 'Completed Quest',
    '3' => 'Having value of Attribute',
);

public function get_new ()
{
	$quest = new stdClass();

	$quest->code = null;
	$quest->name = '';
	$quest->info = '';
	$quest->image = '';
	$quest->reward_id = '';
	$quest->autostart = '';
	$quest->region_id = null;
	$quest->required_completed_quest_id = '';
	$quest->duration = '';
	$quest->completion_requirement_type = '';
	$quest->completion_requirement = '';

	return $quest;
}

public function get_for_dropdown(){
	$empty = array('' => 'No item');
	$result = $this->get_array();		
	$result = array_column($result, 'name', 'id');
	$result = $empty + $result;		
	return $result;
}

public function get_by_for_dropdown($where, $single = FALSE){
	$empty = array('' => 'No item');
	$result = $this->get_array_by($where, $single = FALSE);		
	$result = array_column($result, 'name', 'id');
	$result = $empty + $result;		
	return $result;
}

}