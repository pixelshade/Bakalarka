<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_quest_objectives extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),	
			'quest_id' => array(
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
			// required_answer(text_input)/required_item(item_id)/required_quest(quest_id)/required_objective(objective_id)
			'type' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),	
			'arg0' => array(
				'type' => 'TEXT',				
			)
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('quest_objectives');
	}

	public function down()
	{
		$this->dbforge->drop_table('quest_objectives');
	}
}