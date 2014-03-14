<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_item_activities extends CI_Migration {

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
				'unsigned' => FALSE,				
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
			'attribute_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => FALSE,
				'default' => NONE_ID,				
			),		
			'attribute_amount' => array(
				'type' => 'INT',
				'constraint' => 11,								
			),	
			'active' => array(
				'type' => 'BOOLEAN',											
			),	
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('item_activities');
	}

	public function down()
	{
		$this->dbforge->drop_table('item_activities');
	}
}