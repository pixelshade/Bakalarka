<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_item_instances extends CI_Migration {

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
			'latitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
				'null' => TRUE,
			),
			'longtitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
				'null' => TRUE,
			),
			'amount' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,				
			),			
			'added_by_user' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,			
			),
			'code' => array(
				'type' => 'TEXT',
				'null' => TRUE,				
			),		
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('item_instances');
	}

	public function down()
	{
		$this->dbforge->drop_table('item_instances');
	}
}