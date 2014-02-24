<?php
class Api extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('region_m');
		$this->load->model('page_m');
		$this->load->model('content_files_model');

		// Fetch navigation
		$this->data['menu'] = $this->page_m->get_nested();
	}

	public function index ()
	{
		// Fetch all worlds
		$this->data['worlds'] = $this->region_m->get();
		
		// Load view
		$this->data['subview'] = 'api/index';
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

	public function login(){
		$rules = $this->user_m->rules;
		$this->form_validation->set_rules($rules);		
		if($this->form_validation->run() == TRUE){
			// login and redirect
			if($this->user_m->login() == TRUE){
				$response['success'] = 1;				
				$response['msg'] = "Logged successfully";
				$resposne['data'] = $this->session->all_userdata();
			} else {
				$this->session->set_flashdata('error', 'That email/password doest exist. too bad');
				$response['success'] = 0;
				$response['msg'] = "Wrong user/password.";
			}

		} else {
			$response['success'] = 0;
			$response['msg'] = "User/password not set";
		}
		echo json_encode($response);
	}

	public function isLoggedIn(){
		if($this->user_m->login() == TRUE){
			$response['success'] = 1;				
			$response['msg'] = "Logged successfully";
			$resposne['data'] = $this->session->all_userdata();
		} else {			
			$response['success'] = 0;
			$response['msg'] = "Not logged in";
		}
		echo json_encode($response);
	}

	public function logout(){
		$this->user_m->logout();
		//redirect('api/isLoggedIn');
	}

	public function getContentFilesList(){
		echo json_encode($this->content_files_model->get_all_names());		
	}

}







