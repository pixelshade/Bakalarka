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


	public function json ($player_lat = NULL, $player_lon = NULL)
	{		
		
		if($player_lat != NULL && $player_lon != NULL){
		// Fetch all 
			$this->data['regions'] = $this->region_m->get_by("(`lat_start`<=$player_lat) AND (`lon_start`<=$player_lon) AND
																(`lat_end`>=$player_lat) AND (`lon_end`>=$player_lon)");
			
			$waza = json_encode($this->data['regions']);
			
			print_r(json_decode($waza));
		} else {			
			echo "<hr>no position given";
		}
	}




}