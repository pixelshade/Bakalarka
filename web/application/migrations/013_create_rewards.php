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
			'name' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),	
			'item_definition_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => FALSE,
				'default' => NONE_ID,
			),		
			'item_amount' => array(
				'type' => 'INT',
				'constraint' => 11,
				'null' => TRUE,					
			),		
			'attribute_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => FALSE,
				'default' => NONE_ID,	
			),	
			'attribute_amount' => array(
				'type' => 'INT',
				'constraint' => 11,
				'null' => TRUE,							
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