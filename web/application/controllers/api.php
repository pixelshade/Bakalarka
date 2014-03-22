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
			$quests = $this->quest_m->get_array_where_in('region_id', $region_ids);			
			$result['regions'] = $regions;
			$result['quests'] = $quests;
				// TODO autostart - teraz sa budu pliest message

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
	}

	public function getContentFilesList(){
		echo json_encode($this->content_files_model->get_all_names());		
	}

	public function hasUserActiveQuest($quest_id){
		$user_id = $this->user_m->get_user_id();	
		$user_quest = $this->user_quest_m->get_array_by("`char_id` =".$user_id." AND `quest_id` = '".$quest_id."'");
		if(!empty($user_quest)){
			return TRUE;
		} 
		return FALSE;
	}


	public function check_qrcode($code = null){		
		if($code != null){
			$user_id = $this->user_m->get_user_id();
			$scanned = $this->user_qrscanned_m->get_by('`qrscanned` = "'.$code.'" AND `char_id` = '.$user_id);
			if(empty($scanned)){
				$user_code['code'] = $code;
				$user_code['char_id'] = $user_id;
				$this->user_qrscanned_m->save($user_code);
				$last_char = substr($code, -1);
				switch ($last_char) {
					case QR_QUEST:
					$quest = $this->quest_m->get_by('`code` = "'.$code.'"', TRUE);
					if(!empty($quest)){
						$quest_id = $quest->id;						
						$this->accept_quest($quest_id);	
					} else {
						$response['success'] = 0;
						$response['msg'] = "Quest doesnt exist";
						echo json_encode($response);	
					}					
					break;

					case QR_REWARD:
					$reward = $this->reward_m->get_by('`code` = "'.$code.'"', TRUE);
					if(!empty($reward)){						
						$response = $this->_giveReward($reward->id, $user_id);
						echo json_encode($response);
					} else {
						$response['success'] = 0;
						$response['msg'] = "Reward doesnt exist";
						echo json_encode($response);	
					}
					break;
					default:
					$response['success'] = 0;
					$response['msg'] = "broken code";	
					echo json_encode($response);					
					break;
				}
			}	
		} else {
			$response['success'] = 0;
			$response['msg'] = "No qrcode set";
			echo json_encode($response);
		}
		
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
				$user_quest_id = $user_quest->id;
				$response['msg'] = "Quest was successfuly completed";
				$data['char_id'] = $user_id;
				$data['quest_id'] = $quest_id;
				$data['time_accepted'] = date('Y-m-d H:i:s');
				$data['completed'] = 1;
				$this->user_quest_m->save($data, $user_quest_id);
				$this->_giveRewardFromQuestId($quest_id,$user_id);
			}
		}
		echo json_encode($response);

	}



	private function _giveItem($item_id, $item_amount, $char_id){						
		if($item_id != NONE_ID && $item_amount != 0){
			$item = $this->item_definition_m->get_by_id($item_id);
			if(!empty($item)){
				$user_item['char_id'] = $char_id;
				$user_item['item_id'] = $item_id;
				for ($i=0; $i < $item_amount; $i++) { 	
					$this->user_item_m->save($user_item);
				}				
				return TRUE;			
			} 
		}
		return FALSE;
	}

	private function _giveAttribute($attribute_id, $attribute_amount, $char_id)
	{
		if($attribute_id != NONE_ID && $attribute_amount != 0){
			$attribute = $this->attribute_m->get_by_id($attribute_id);
			if(!empty($attribute)){
				$user_attribute['char_id'] = $char_id;
				$user_attribute['attribute_id'] = $attribute_id;
				for ($i=0; $i < $attribute_amount; $i++) { 				
					$this->user_attribute_m->save($user_attribute);
				}
				return TRUE;
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
		$response['msg'] = 'Reward wasnt received.';			
		$response['success'] = 0;	
		return $response;
	}

	private function _giveReward($reward_id, $char_id){		
		$reward = $this->reward_m->get_by_id($quest->reward_id);
		if(!empty($reward)){
			$item_id = $reward->item_definition_id;
			$item_amount = $reward->item_amount;				
			$is_item_given = $this->_giveItem($item_id, $item_amount, $char_id);

			$attribute_id = $reward->attribute_id;
			$attribute_amount = $reward->attribute_amount;
			$is_attribute_given = $this->_giveAttribute($attribute_id, $attribute_amount, $char_id);

			$response['type'] = 'GIVE_REWARD';						
			if($is_attribute_given){
				$attribute = $this->attribute_m->get_by_id($attribute_id);
				$response['msg'] = 'You received attribute:'.$attribute->name.' '.$attribute->amount;				
				$response['data']['attribute'] = $attribute;
				$response['success'] = 1;
			}
			if($is_item_given){
				$item = $this->item_definition_m->get_by_id($item_id);
				$response['msg'] = 'Received reward.';			
				$response['data']["item"] = $item;
				$response['success'] = 1;
			} 
			if(!$is_attribute_given && !$is_item_given){
				$response['msg'] = 'Reward wasnt received.';			
				$response['success'] = 0;	
			}
			
			return $response;
		}

	} 



}






