<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_characters extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),
			'user_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,				
			),
			'name' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),
			'latitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),
			'longtitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),			
			'inventory_json' => array(
				'type' => 'TEXT',				
			),	
			'stats_json' => array(
				'type' => 'TEXT',				
			),		
					
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('characters');
	}

	public function down()
	{
		$this->dbforge->drop_table('characters');
	}
}