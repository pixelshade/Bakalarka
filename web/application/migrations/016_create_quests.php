<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_quests extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),	
			'region_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE
			),				
			'name' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),
			'info' => array(
				'type' => 'TEXT',				
			),
			'reward_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE
			),	
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('quests');
	}

	public function down()
	{
		$this->dbforge->drop_table('quests');
	}
}