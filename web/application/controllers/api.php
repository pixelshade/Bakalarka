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
		$this->load->model('item_instance_m');
		$this->load->model('user_quest_m');
		$this->load->model('user_item_m');
		$this->load->model('user_attribute_m');
		$this->load->model('user_qrscanned_m');
		$this->load->model('user_position_m');

		// Fetch navigation
		$this->data['menu'] = $this->page_m->get_nested();
	}

	public function index ()
	{
		// Fetch all worlds
		$this->data['functions'] = get_class_methods($this);

		// Load view
		$this->data['subview'] = 'api/index';
		$this->load->view('_layout_main', $this->data);
	}

	public function json ($player_lat = NULL, $player_lon = NULL)
	{		

		$user_id = $this->user_m->get_user_id();	
		if($player_lat != NULL && $player_lon != NULL){
		// Fetch all 			
			$this->user_position_m->save($user_id, $player_lat, $player_lon);
			$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
			$region_ids = array_column($regions, 'id');					
			// $active_completed_quests =	$this->user_quest_m->get_array_by('char_id = "'.$user_id.'" AND ');		
			// $active_completed_quests_ids =	array_column($active_completed_quests, 'quest_id');

			// $quests = $this->quest_m->get_array_where_in('region_id', $region_ids, 'quest_id', $active_completed_quests_ids);	
			$quests = $this->quest_m->get_array_where_in('region_id', $region_ids);	
			$result['regions'] = $regions;
			$result['quests'] = $quests;
				// TODO autostart - teraz sa budu pliest message
			$response = array();
			foreach ($quests as $quest) {			
				if($quest['autostart']){
					$response[] = $this->accept_quest($quest['id'], false);
				}
			}
			$user_items = $this->user_item_m->get_array_by("`char_id` = '".$user_id."'");
			$item_ids = array_column($user_items,"item_id");
			$items = $this->item_definition_m->get_array_where_in('id',$item_ids);

			$result['items'] = $items;	
			$result['responses'] = $response;
		// Print all	
			$result = json_encode($result);
			echo $result;
		} else {			
			echo "<hr>no position given";
		}
	}

	public function register(){
		$response['type'] = "REGISTER_USER";
		$rules = $this->user_m->rules_register;		
		$this->form_validation->set_rules($rules);		
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_m->array_from_post(array('email', 'password'));			
			$data['rights_level'] = $this->user_m->default_rights_level;
			$data['password'] = $this->user_m->hash($data['password']);
			$this->user_m->save($data);
			$response['success'] = 1;
			$response['msg'] = 'Registered succesfully';
		} else {
			$response['success'] = 0;
			$response['msg'] = strip_tags(validation_errors());	
			//todo nastavit oddelovace chyb/ spravit custom chybu		
		}
		echo json_encode($response);
	}

	public function login(){
		$response['type'] = "LOGIN";
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
		$response['type'] = "IS_LOGGED";	
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
	}

	public function getContentFilesList(){
		echo json_encode($this->content_files_model->get_all_names());		
	}

	public function hasUserActiveQuest($quest_id = null){
		$user_id = $this->user_m->get_user_id();	
		$user_quest = $this->user_quest_m->get_array_by("`char_id` =".$user_id." AND `quest_id` = '".$quest_id."'");
		if(!empty($user_quest)){
			return TRUE;
		} 
		return FALSE;
	}

	public function remove_my_active_quest($quest_id = null){
		if($quest_id==null){
			$response['success'] = 0;
			$response['msg'] = 'Quest not specified';
		} else {
			$user_id = $this->user_m->get_user_id();
			$quest = $this->user_quest_m->get_by("`char_id` = '".$user_id."' AND `quest_id` = '".$quest_id."' AND `completed` = '0'");
			if(empty($quest)) {
				$response['success'] = 0;
				$response['msg'] = 'Active quest not found';
			} else {
				$this->user_quest_m->delete("char_id =".$user_id);		
				$response['success'] = 1;
				$response['msg'] = 'Active quest deleted';
			}		
		} 
		echo json_encode($response);	
	}

	public function get_my_attributes(){
		$user_id = $this->user_m->get_user_id();
		$attributes = $this->user_attribute_m->get_array_by("`char_id` = '".$user_id."'");				
		echo json_encode($attributes);
	}


	public function get_my_quests(){
		$user_id = $this->user_m->get_user_id();
		$quests = $this->user_quest_m->get_array_by("`char_id` = '".$user_id."'");		
		// print_r($this->user_quest_m);
		echo json_encode($quests);
	}

	public function get_my_items(){
		$user_id = $this->user_m->get_user_id();		
		$items = $this->user_item_m->get_array_by("`char_id` = '".$user_id."'");		
		echo json_encode($items);
	}



	public function check_qrcode($code = null){		
		if($code != null){
			$user_id = $this->user_m->get_user_id();
			$scanned = $this->user_qrscanned_m->get_by('`qrscanned` = "'.$code.'" AND `char_id` = '.$user_id);
			if(empty($scanned)){				
				$last_char = substr($code, -1);
				switch ($last_char) {
					case QR_QUEST:
					$quest = $this->quest_m->get_by('`code` = "'.$code.'"', TRUE);
					if(!empty($quest)){
						$this->user_qrscanned_m->insert($user_id,$code);
						$quest_id = $quest->id;						
						$response = $this->accept_quest($quest_id);	
					} else {
						$response['success'] = 0;
						$response['msg'] = "Quest doesnt exist";						
					}					
					break;

					case QR_REWARD:
					$reward = $this->reward_m->get_by('`code` = "'.$code.'"', TRUE);
					if(!empty($reward)){						
						$this->user_qrscanned_m->insert($user_id,$code);
						$response = $this->_giveReward($reward->id, $user_id);						
					} else {
						$response['success'] = 0;
						$response['msg'] = "Reward doesnt exist";						
					}
					break;
					default:
					$response['success'] = 0;
					$response['msg'] = "broken code";											
					break;
				}
			} else {
				$response['success'] = 0;
				$response['msg'] = "QR code already scanned";
			}	
		} else {
			$response['success'] = 0;
			$response['msg'] = "No qrcode set";
		}
		echo json_encode($response);		
	}


	private function _found_item_info($item_id){

	}


	public function accept_quest($quest_id = NULL, $echo_response = TRUE){
		$response['type'] = "ACCEPT_QUEST";
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
		if($echo_response){
			echo json_encode($response);
		} else {
			return $response;
		}
	}

	public function complete_quest($quest_id = NULL, $player_lat = NULL, $player_lon = NULL,$answer = NULL){
		$response['type'] = "COMPLETE_QUEST";
		$user_id = $this->user_m->get_user_id();	
		$success = false;	
		if($quest_id == NULL){
			$response['success'] = 0;
			$response['msg'] = "Quest id unspecified";
		} else {			
			$is_completed = $this->user_quest_m->is_quest_completed_for_char_id($quest_id, $user_id);
			if($is_completed){
				$response['success'] = 0;
				$response['msg'] = "Quest is already completed";	
			} else {			
				$quest = $this->quest_m->get_by("`id` = '".$quest_id."'", TRUE);
				if($quest){
					$response['success'] = 0;
					switch ($quest->completion_requirement_type) {	
						case 0:		// type answer	
						$success = ($quest->completion_requirement == $answer);
						if($success) {
							$response['success'] = 1;					
						} else {
							$resoibse['success'] = 0;
							$response['msg'] =  "Answer is wrong";
						}
						break;

						case 1:		// have an item
						$required_item_id = $quest->completion_requirement;
						$user_item = $this->user_item_m->get_array_by('`char_id` = `'.$char_id.'` AND `item_id` = `'.$required_item_id.'`');
						if(count($user_item)){
							$response['success'] = 1;					
						} else {
							$response['success'] = 0;
							$response['msg'] =  "You don't have required item.";
						}				 
						break;

						case 2:		// Completed other quest
						$required_completed_quest_id = $quest->completion_requirement;
						$user_quest = $this->user_quest_m->get_array_by('`char_id` = `'.$char_id.'` AND `quest_id` = `'.$required_completed_quest_id.'` AND `completed` = 1', TRUE);
						if(count($user_quest)){
							$response['success'] = 1;					
						} else {
							$response['success'] = 0;
							$response['msg'] =  "You must complete other quest.";
						}				
						break;

						case 3:		// Having value of Attribute
						// TODO problem with need of attribute id and attribute value
						// possible id#value 				
						break;

						case 4:		// Being in region
						if($player_lon == NULL || $player_lat == NULL){
							$response['success'] = 0;
							$response['msg'] = "Players latitude and longtitude must be specified.";
						} else {
							$required_region_id = $quest->completion_requirement;
							$regions = $this->region_m->get_by_latlon($player_lat,$player_lon);
							$region_ids = array_column($regions, 'id');				
							$success = in_array($required_region_id, $region_ids);				
							if($success){
								$response['success'] = 1;
							} else {
								$response['success'] = 0;
								$response['msg'] = "You are not in required region.";
							}
						}
						break;
						default:
						$response['msg'] =  "Uknown quest type";
						$response['success'] = 0;
						break;
					}
				} else {
					$response['msg'] =  "Quest doesnt exist";
					$response['success'] = 0;
				}
			}

			if($success){
				$user_quest = $this->user_quest_m->get_by("`char_id` = '".$user_id."' AND `quest_id` = '".$quest_id."'", TRUE);		
				$user_quest_id = empty($user_quest) ? NULL : $user_quest->id;				
				$response['msg'] = "Quest was successfuly completed";
				$data['char_id'] = $user_id;
				$data['quest_id'] = $quest_id;
				$data['time_accepted'] = date('Y-m-d H:i:s');
				$data['completed'] = 1;
				// $this->user_quest_m->save($data, $user_quest_id);
				$response['data'] = $this->_giveRewardFromQuestId($quest_id,$user_id);				
			}
		}
		echo json_encode($response);

	}



	private function _giveItem($item_id, $item_amount, $char_id){						
		if($item_id != NONE_ID && $item_amount != 0){
			$item = $this->item_definition_m->get_by_id($item_id);
			if(!empty($item) && $item_amount!=0){
				$item->amount = $item_amount;
				$user_item['char_id'] = $char_id;
				$user_item['item_id'] = $item_id;

				$existing = $this->user_item_m->get_array_by('`char_id` = "'.$char_id.'" AND `item_id` = "'.$item_id.'"', TRUE);				
				if(empty($existing)){
					$existing_id = NULL;
					$user_item['amount'] = $item_amount;
				} else {
					$existing_id = $existing['id'];               
					$user_item['amount'] = $existing['amount'] + $item_amount;               
				}
				
				$this->user_item_m->save($user_item, $existing_id);				
				return $item;			
			} 
		}
		return FALSE;
	}

	private function _giveAttribute($attribute_id, $attribute_amount, $char_id)
	{
		if($attribute_id != NONE_ID && $attribute_amount != 0){
			$attribute = $this->attribute_m->get_by_id($attribute_id);
			if(!empty($attribute)){
				$attribute->amount = $attribute_amount;
				$user_attribute['char_id'] = $char_id;
				$user_attribute['attribute_id'] = $attribute_id;
				$user_attribute['amount'] = $attribute_amount;

				$existing = $this->user_attribute_m->get_array_by('`char_id` = "'.$char_id.'" AND `attribute_id` = "'.$attribute_id.'"', TRUE);				
				if(empty($existing)){
					$existing_id = NULL;
					$user_attribute['amount'] = $attribute_amount;
				} else {
					$existing_id = $existing['id'];               
					$user_attribute['amount'] = $existing['amount'] + $attribute_amount;               
				}
				
				$this->user_attribute_m->save($user_attribute, $existing_id);

				return $attribute;
			}
		}
		return FALSE;	
	}

	private function _giveRewardFromQuestId($quest_id, $char_id){
		$quest = $this->quest_m->get_by_id($quest_id);
		if($quest!=null){
			if($quest->reward_id != NONE_ID){
				return $this->_giveReward($quest->reward_id, $char_id);
			}
		}
		$response['msg'] = 'Reward wasnt received. Quest is null or has no reward.';			
		$response['success'] = 0;		
		return $response;
	}

	private function _giveReward($reward_id, $char_id){		
		$reward = $this->reward_m->get_by_id($reward_id);
		if(!empty($reward)){
			$item_id = $reward->item_definition_id;
			$item_amount = $reward->item_amount;				
			$item_given = $this->_giveItem($item_id, $item_amount, $char_id);

			$attribute_id = $reward->attribute_id;
			$attribute_amount = $reward->attribute_amount;
			$attribute_given = $this->_giveAttribute($attribute_id, $attribute_amount, $char_id);

			$response['type'] = 'GIVE_REWARD';						
			if(!empty($attribute_given)){				
				$attribute_given->amount = $attribute_amount;
				$response['data']['attribute'] = $attribute_given;								
			}
			if(!empty($item_given)){		
				$item_given->amount = $item_amount;		
				$response['data']["item"] = $item_given;				
			} 

			// if no item nor attribute was given
			if(!$attribute_given && !$item_given){
				$response['msg'] = 'Reward wasn\'t received.';			
				$response['success'] = 0;	
			} else {
				$response['msg'] = 'You received reward.';
				$response['success'] = 1;
			}
			
			return $response;
		}

	} 






}






