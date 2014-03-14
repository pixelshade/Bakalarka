<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_enemy_definitions extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),			
			'name' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),
			'info' => array(
				'type' => 'TEXT',				
			),
			'image' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),		
			'stats_json' => array(
				'type' => 'TEXT',				
			),			
			'reward_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => FALSE,
				'default' => NONE_ID,				
			),				
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('enemy_definitions');
	}

	public function down()
	{
		$this->dbforge->drop_table('enemy_definitions');
	}
}