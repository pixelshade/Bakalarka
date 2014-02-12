<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_regions extends CI_Migration {

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
			'info' => array(
				'type' => 'TEXT',				
			),
			'image' => array(
				'type' => 'VARCHAR',
				'constraint' => '100',
			),		
			'lat_start' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),
			'lon_start' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),
			'lat_end' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),
			'lon_end' => array(
				'type' => 'DECIMAL',
				'constraint' => '15,13',
			),			
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('regions');
	}

	public function down()
	{
		$this->dbforge->drop_table('regions');
	}
}