<?php
class World extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('region_m');
		$this->load->model('page_m');
		
		// Fetch navigation
		$this->data['menu'] = $this->page_m->get_nested();
	}

	public function index ()
	{
		// Fetch all worlds
		$this->data['worlds'] = $this->region_m->get();
		
		// Load view
		$this->data['subview'] = 'world/index';
		$this->load->view('_layout_main', $this->data);
	}

}