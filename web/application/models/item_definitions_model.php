<?php

/**
* Item definition model
*/
class Item_definition_model extends CI_Model
{
	
	function __construct()
	{
		$this->load->database();
	}

	public function get_item_instance($slug = FALSE)
	{
		if ($slug === FALSE)
		{
			$query = $this->db->get('news');
			return $query->result_array();
		}		
		$query = $this->db->get_where('news', array('slug' => $slug));
		return $query->row_array();
	}

	public function set_item_instance()
	{
		
		$data = array(
			'name' => $this->input->post('name'),
			'info' => $this->input->post('info'),
			'image' => $this->input->post('image'),
			'item_activity_id' => $this->input->post('item_activity_id')
			);

		return $this->db->insert('Item_definition_model', $data);
	}
}