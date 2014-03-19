<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_user_position extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),			
			'char_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
			),				
			'latitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '9,7',
			),
			'longtitude' => array(
				'type' => 'DECIMAL',
				'constraint' => '9,7',
			),
			'time' => array(
				'type' => 'DATETIME',
			),
		
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('user_position');
	}

	public function down()
	{
		$this->dbforge->drop_table('user_position');
	}
}