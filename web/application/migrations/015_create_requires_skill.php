<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_requires_skill extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),			
			'skill_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),	
			'requires_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE				
			),		
			
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('requires_skill');
	}

	public function down()
	{
		$this->dbforge->drop_table('requires_skill');
	}
}