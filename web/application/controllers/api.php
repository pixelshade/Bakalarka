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
		$this->load->model('user_quest_m');
		$this->load->model('user_item_m');
		$this->load->model('user_attribute_m');

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

	public function hasUserActiveQuest($quest_id){
		$user_id = $this->user_m->get_user_id();	
		$user_quest = $this->user_quest_m->get_array_by("`char_id` =".$user_id." AND `quest_id` =".$quest_id);
		if(!empty($user_quest)){
			return TRUE;
		}
		return FALSE;
	}

	public function check_quest_completion($quest_id, $player_lat = NULL, $player_lon = NULL,$answer = NULL){
		$user_id = $this->user_m->get_user_id();		

		$quest = $this->quest_m->get_by("`id` = '".$quest_id."'", TRUE);
		if($quest){
			switch ($quest->completion_requirement_type) {
			// type answer
				case 0:				
				return ($quest->completion_requirement == $answer);
				break;

			// have an item
				case 1:
				$required_item_id = $quest->completion_requirement;
				$user_item = $this->user_item_m->get_array_by('`char_id` = `'.$char_id.'` AND `item_id` = `'.$required_item_id.'`');
				if(count($user_item)){
					return TRUE;
				} else {
					return FALSE;
				}				 
				break;

			// Completed other quest
				case 2:
				$required_completed_quest_id = $quest->completion_requirement;
				$user_quest = $this->user_item_m->get_array_by('`char_id` = `'.$char_id.'` AND `quest_id` = `'.$required_completed_quest_id.'`', TRUE);
				if(count($user_quest)){
					return TRUE;
				} else {
					return FALSE;
				}				
				break;

			// Having value of Attribute
				case 3:
				// TODO problem with need of attribute id and attribute value
				// possible id#value 				
				break;

			// Being in region
				case 4:
				$required_region_id = $quest->completion_requirement;
				$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
				$region_ids = array_column($regions, 'id');				
				return in_array($required_region_id, $region_ids);				
				break;

				default:
				echo "uknown type";
				return FALSE;
				break;
			}
		} else {
			echo "quest doesnt exist";
			return FALSE;
		}

	}
}







