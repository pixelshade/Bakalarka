<?php
class Migration_Movement_col_to_regions extends CI_Migration {

	public function up()
	{
		$fields = (array(
			'movement' => array(
				'type' => 'TEXT',
				'default' => ''
			),
		));
		$this->dbforge->add_column('regions', $fields);
	}

	public function down()
	{
		$this->dbforge->drop_column('regions', 'movement');
	}
}