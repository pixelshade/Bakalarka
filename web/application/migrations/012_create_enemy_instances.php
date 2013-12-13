<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_enemy_instances extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),			
			'enemy_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),		
			'latitude' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),
			'longitude' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),	
			'amount' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,				
			),		

		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('enemy_instances');
	}

	public function down()
	{
		$this->dbforge->drop_table('enemy_instances');
	}
}