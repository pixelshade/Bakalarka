<?php
class Cron_task_m extends MY_Model
{
	protected $_table_name = 'cron_tasks';
	protected $_order_by = 'name, id desc';
	public $rules = array(
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
			), 
		'json_params' => array(
			'field' => 'json_params', 
			'label' => 'Info', 
			'rules' => 'trim|required'
			),	
		'task_type' => array(
			'field' => 'task_type', 
			'label' => 'Latitude start', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			),
		
		);

	public function get_new ()
	{
		$cron_task = new stdClass();

		$cron_task->name = '';
		$cron_task->json_params = '';		
		$cron_task->task_type = '';	

		return $cron_task;
	}

	

}