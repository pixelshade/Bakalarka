<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_region_movement extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),
			'region_id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,				
			),
			'json_params' => array(
				'type' => 'TEXT',				
			),				
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('region_movement');
	}

	public function down()
	{
		$this->dbforge->drop_table('region_movement');
	}
}