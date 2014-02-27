<?php
class Region_m extends MY_Model
{
	protected $_table_name = 'regions';
	protected $_order_by = 'name, id desc';
	public $rules = array(
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
			), 
		'info' => array(
			'field' => 'info', 
			'label' => 'Info', 
			'rules' => 'trim|required'
			),
		'image' => array(
			'field' => 'image', 
			'label' => 'Image', 
			'rules' => 'trim|max_length[255]|xss_clean'
			), 
		'lat_start' => array(
			'field' => 'lat_start', 
			'label' => 'Latitude start', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lon_start' => array(
			'field' => 'lon_start', 
			'label' => 'Longtitude start', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lat_end' => array(
			'field' => 'lat_end', 
			'label' => 'Latitude end', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 
		'lon_end' => array(
			'field' => 'lon_end', 
			'label' => 'Longtitude end', 
			'rules' => 'trim|required|decimal[10]|xss_clean'
			), 		
		);

	public function get_new ()
	{
		$region = new stdClass();

		$region->name = '';
		$region->info = '';
		$region->image = '';
		$region->lat_start = '';
		$region->lon_start = '';
		$region->lat_end = '';
		$region->lon_end = '';

		return $region;
	}

	public function get_for_dropdown(){
		$empty = array('' => 'No item');
		$result = (array)$this->get_array();		
		$result = array_column($result, 'name', 'id');
		$result = array_merge($empty,$result);		
		return $result;
	}

}