<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Migration_Create_content_files extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'id' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'auto_increment' => TRUE
			),						
			'filename' => array(
				'type' => 'VARCHAR',
				'constraint' => '255',
			)	
		));
		$this->dbforge->add_key('id', TRUE);
		$this->dbforge->create_table('content_files');
	}

	public function down()
	{
		$this->dbforge->drop_table('content_files');
	}
}
