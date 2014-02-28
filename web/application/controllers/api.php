<?php
class Api extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('region_m');
		$this->load->model('quest_m');
		$this->load->model('attribute_m');
		$this->load->model('item_definition_m');
		$this->load->model('reward_m');		
		$this->load->model('page_m');
		$this->load->model('content_files_model');

		// Fetch navigation
		$this->data['menu'] = $this->page_m->get_nested();
	}

	public function index ()
	{
		// Fetch all worlds
		$this->data['functions'] = get_class_methods($this);
		//dump($this->data['functions']);
		// Load view
		$this->data['subview'] = 'api/index';
		$this->load->view('_layout_main', $this->data);
	}

	public function json ($player_lat = NULL, $player_lon = NULL)
	{		
		
		if($player_lat != NULL && $player_lon != NULL){
		// Fetch all 			
			$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
			$region_ids = array_column($regions, 'id');
			$quests = $this->quest_m->get_array_where_in('region_id', $region_ids);			
			$result['regions'] = $regions;
			$result['quests'] = $quests;
		// Print all	
			$result = json_encode($result);
			echo $result;
			// print_r(json_decode($result));
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
		if($this->user_m->loggedin() == TRUE){
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

	public function serverSettings(){
		echo json_encode(array('to' => 'do'));
	}

}







