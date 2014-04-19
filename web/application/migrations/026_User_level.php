<?php
class Migration_User_level extends CI_Migration {

	public function up()
	{
		$fields = (array(
			'rights_level' => array(
				'type' => 'INT',
				'constraint' => 11,
				'unsigned' => TRUE,
				'default' => 0
			),
		));
		$this->dbforge->add_column('users', $fields);
	}

	public function down()
	{
		$this->dbforge->drop_column('users', 'rights_level');
	}
}