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
			'code' => array(
				'type' => 'VARCHAR',
				'constraint' => '50',
				'null' => TRUE,	 
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
			'reward_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'null' => TRUE,	
			),
			'autostart'	=> array(
				'type' => 'BOOLEAN',
			),
			'region_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'null' => TRUE,	
			),
			'required_completed_quest_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'null' => TRUE,	
			),				
			'duration' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'null' => TRUE,	
			),					
			'completion_requirement_type' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),	
			'completion_requirement' => array(
				'type' => 'TEXT',				
			)					
			
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('quests');
	}

	public function down()
	{
		$this->dbforge->drop_table('quests');
	}
}