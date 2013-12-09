<?php
 /**
 * Item instance
 */
 class Item_instance_model extends CI_Model
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
			'item_def_id' => $this->input->post('item_def_id'),
			'lat' => $this->input->post('lat'),
			'lon' => $this->input->post('lon'),
			'amount' => $this->input->post('amount'),
			'added_by' => $this->input->post('added_by'),
			'code' => $this->input->post('code')
			);

		return $this->db->insert('news', $data);
	}
 }