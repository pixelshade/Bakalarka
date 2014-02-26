<?php
class User_objective_m extends MY_Model
{
	protected $_table_name = 'user_objectives';
	protected $_order_by = 'char_id, id desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'Name', 
			'rules' => 'intval'
			), 
		'objective_id' => array(
			'field' => 'objective_id', 
			'label' => 'objective_id', 
			'rules' => 'intval'
			),	
		'completed' => array(
			'field' => 'completed', 
			'label' => 'completed', 
			'rules' => 'intval'			
			)	
		);

	public function get_new ()
	{
		$user_objective = new stdClass();

		$user_objective->char_id = '';
		$user_objective->objective_id = '';
		$user_objective->completed = '';

		return $user_objective;
	}

}