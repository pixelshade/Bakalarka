<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_user_objectives extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),
			'char_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),	
			'objective_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),		
			'completed' => array(
				'type' => 'BOOLEAN',				
			)		
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('user_objectives');
	}

	public function down()
	{
		$this->dbforge->drop_table('user_objectives');
	}
}