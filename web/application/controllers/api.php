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

		$user_id = $this->user_m->get_user_id();	
		
		if($player_lat != NULL && $player_lon != NULL){
		// Fetch all 			
			$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
			$region_ids = array_column($regions, 'id');					
			$quests = $this->quest_m->get_array_where_in('region_id', $region_ids);			
			$result['regions'] = $regions;
			$result['quests'] = $quests;
			$items = $this->user_item_m->get_by("`char_id` = ".$user_id."");
			$result['items'] = $items;				
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


	public function accept_quest($quest_id = NULL){
		$user_id = $this->user_m->get_user_id();
		if($quest_id == NULL){
			$response['success'] = 0;
			$response['msg'] = "Quest id unspecified";
		} else {	
			$quest = $this->quest_m->get_by_id($quest_id);
			if(empty($quest)){
				$response['success'] = 0;
				$response['msg'] = "Quest doesnt exist";	
			} else {
				if($this->user_quest_m->is_quest_accepted_for_char_id($quest_id, $user_id)){
					$response['success'] = 0;
					$response['msg'] = "Quest is already accepted";
				} else {
					$required_quest_id = $quest->required_completed_quest_id;	
					if($required_quest_id == NONE_ID){						
						$accept = TRUE;
					} else {
						if($this->user_quest_m->is_quest_completed_for_char_id($required_quest_id,$user_id)){							
							$accept = TRUE;	
						} else {
							$accept = FALSE;
							$response['success'] = 0;
							$response['msg'] = "Another quest must be completed before this can be accepted.";	
						}
					}
					if($accept){
						$data['char_id'] = $user_id;
						$data['quest_id'] = $quest_id;
						$data['time_accepted'] = date('Y-m-d H:i:s');
						$data['completed'] = 0;
						$this->user_quest_m->save($data);
						$response['success'] = 1;
						$response['msg'] = "Quest accepted";
					}
				}
			}
		}
		echo json_encode($response);
	}

	public function complete_quest($quest_id, $player_lat = NULL, $player_lon = NULL,$answer = NULL){
		$user_id = $this->user_m->get_user_id();		

		$quest = $this->quest_m->get_by("`id` = '".$quest_id."'", TRUE);
		if($quest){
			$response['success'] = FALSE;
			switch ($quest->completion_requirement_type) {			
				case 0:		// type answer		
				$response['success'] = ($quest->completion_requirement == $answer);
				break;
				case 1:		// have an item
				$required_item_id = $quest->completion_requirement;
				$user_item = $this->user_item_m->get_array_by('`char_id` = `'.$char_id.'` AND `item_id` = `'.$required_item_id.'`');
				if(count($user_item)){
					$response['success'] = TRUE;
				} else {
					$response['success'] = FALSE;
				}				 
				break;
				case 2:		// Completed other quest
				$required_completed_quest_id = $quest->completion_requirement;
				$user_quest = $this->user_item_m->get_array_by('`char_id` = `'.$char_id.'` AND `quest_id` = `'.$required_completed_quest_id.'`', TRUE);
				if(count($user_quest)){
					$response['success'] = TRUE;
				} else {
					$response['success'] = FALSE;
				}				
				break;
				case 3:		// Having value of Attribute
				// TODO problem with need of attribute id and attribute value
				// possible id#value 				
				break;
				case 4:		// Being in region
				$required_region_id = $quest->completion_requirement;
				$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
				$region_ids = array_column($regions, 'id');				
				$response['success'] = in_array($required_region_id, $region_ids);				
				break;
				default:
				$response['msg'] =  "Uknown quest type";
				$response['success'] = FALSE;
				break;
			}
		} else {
			$response['msg'] =  "Quest doesnt exist";
			$response['success'] = FALSE;
		}



		echo json_encode($response);
	}
}







