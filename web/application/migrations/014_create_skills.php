<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_skills extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),	
			'id' => array(
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
			'image' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),
			'attribute_to_change' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE
			),
			'amount' => array(
				'type' => 'INT',
				'constraint' => 11				
			),		
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('skills');
	}

	public function down()
	{
		$this->dbforge->drop_table('skills');
	}
}