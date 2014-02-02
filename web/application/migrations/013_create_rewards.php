<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_rewards extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),		
			'item_definition_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE
			),		
			'experience' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE
			),	
			'gold' => array(
				'type' => 'INT',
				'constraint' => 11						
			),	
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('rewards');
	}

	public function down()
	{
		$this->dbforge->drop_table('rewards');
	}
}