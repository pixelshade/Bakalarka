// <?php
// class Region_model extends CI_Model{
	
// 	function __construct()
// 	{
// 		$this->load->database();
// 	}

// 	public function get_regions()
// 	{
// 		$query = $this->db->get('regions');
// 		return $query->result_array();
// 	}

// 	public function get_region($id = FALSE)
// 	{
// 		if ($id === FALSE)
// 		{
// 			$query = $this->db->get('id');
// 			return $query->result_array();
// 		}		
// 		$query = $this->db->get_where('regions', array('id' => $id));
// 		return $query->row_array();
// 	}

// 	public function set_region()
// 	{
// 		$this->load->helper('url');

// 		//$id = url_title($this->input->post('name'), 'dash', TRUE);

// 		$data = array(
// 			'name' => $this->input->post('name'),
// 			'info' => $this->input->post('info'),
// 			'image' => $this->input->post('image'),
// 			'lat_start' => $this->input->post('lat_start'),
// 			'lon_start' => $this->input->post('lon_start'),
// 			'lat_end' => $this->input->post('lat_end'),
// 			'lon_end' => $this->input->post('lon_end'),
// 			//'id' => $id,			
// 			);

// 		return $this->db->insert('regions', $data);
// 	}
// }