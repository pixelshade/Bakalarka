<?php
class World extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('region_m');
	}

	public function index ()
	{
		// Fetch all worlds
		$this->data['worlds'] = $this->region_m->get();
		
		// Load view
		$this->data['subview'] = 'world/index';
		$this->load->view('_layout_main', $this->data);
	}


	public function json ($position = NULL)
	{
		$position = json_decode($position);
		// $position['longtitude']
		// $position['latitude']   =
		// Fetch all worlds
		$this->data['regions'] = $this->region_m->get();
		
		echo $waza = json_encode($this->data['regions']);
		// echo "<hr>";
		// print_r(json_decode($waza));
	}




}