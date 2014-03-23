<?php
/**
* MY_Model
*/	
class MY_Model extends CI_Model
{

	protected $_table_name = '';
	protected $_primary_key = 'id';
	protected $_primary_filter = 'intval';
	protected $_order_by = '';
	public $_rules = array();
	protected $_timestamps = FALSE;


	function __construct()
	{
		parent::__construct();
	}

	public function array_from_post($fields){
		$data = array();
		foreach ($fields as $field) {
			$data[$field] = $this->input->post($field);
		}
		return $data;
	}

	public function get($id = NULL, $single = FALSE){
		if($id != NULL){
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->where($this->_primary_key, $id);
			$method = 'row';

		} elseif($single == TRUE){
			$method = 'row';

		} else {
			$method = 'result';
		}

		if(!count($this->db->ar_orderby)){
			$this->db->order_by($this->_order_by);

		}
		return $this->db->get($this->_table_name)->$method();
	}


	public function get_array($id = NULL, $single = FALSE){
		if($id != NULL){
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->where($this->_primary_key, $id);
			$method = 'row_array';

		} elseif($single == TRUE){
			$method = 'row_array';

		} else {
			$method = 'result_array';
		}

		if(!count($this->db->ar_orderby)){
			$this->db->order_by($this->_order_by);

		}
		return $this->db->get($this->_table_name)->$method();
	}

	public function get_by($where, $single = FALSE){
		$this->db->where($where);
		return $this->get(NULL, $single);
	}

	public function get_by_id($id){
		$this->db->where($this->_primary_key,$id);
		return $this->get(NULL, TRUE);
	}

	public function get_array_by($where, $single = FALSE){
		$this->db->where($where);
		return $this->get_array(NULL, $single);
	}

	public function get_array_where_in($where, $in_array, $or_where = NULL, $or_in = NULL){
		if(empty($in_array)) return $in_array;

		$this->db->where_in($where, $in_array);

		if($or_where != NULL && $or_in != NULL && !empty($or_in)){			
			$this->db->or_where_in($or_where, $or_in);
		}
		return $this->get_array();
	}	

	public function save($data, $id = NULL){
		//Set timestamps
		if($this->_timestamps == TRUE) {
			$now = date('Y-m-d H:i:s');
			$id || $data['created'] = $now;
			$data['modified'] = $now;
		}

		//insert
		if($id === NULL){
			!isset($data[$this->_primary_key]) || $data[$this->_primary_key] = NULL;
			$this->db->set($data);
			$this->db->insert($this->_table_name);
			$id = $this->db->insert_id();
		}
		// Update
		else {			
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->set($data);
			$this->db->where($this->_primary_key, $id);
			$this->db->update($this->_table_name);
		}
		return $id;
	}

	public function delete($id){		
		$filter = $this->_primary_filter;
		$id = $filter($id);

		if(!$id){
			return FALSE;
		}

		$this->db->where($this->_primary_key, $id);
		$this->db->limit(1);
		$this->db->delete($this->_table_name);
		return TRUE;
	}

}