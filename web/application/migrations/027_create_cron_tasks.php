<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_cron_tasks extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),
			'task_type' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,				
			),
			'name' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),	
		
			'json_params' => array(
				'type' => 'TEXT',				
			),	
			'active' => array(
				'type' => 'INT',
				'constraint' => 2,
				'unsigned' => TRUE,				
			),		
			'time_to_run' => array(
				'type' => 'VARCHAR',
				'constraint' => '20',								
			),	
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('cron_tasks');
	}

	public function down()
	{
		$this->dbforge->drop_table('cron_tasks');
	}
}