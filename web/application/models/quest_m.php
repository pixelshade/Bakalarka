<?php
class Quest_m extends MY_Model
{
	protected $_table_name = 'quests';
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
		'reward_id' => array(
			'field' => 'reward_id', 
			'label' => 'Reward_id', 
			'rules' => 'trim|intval'
			), 
		'autostart' => array(
			'field' => 'autostart', 
			'label' => 'autostart', 
			'rules' => 'trim|max_length[255]|xss_clean'
			), 
		'region_id' => array(
			'field' => 'region_id', 
			'label' => 'region_id', 
			'rules' => 'trim|intval'
			), 
		'required_completed_quest_id' => array(
			'field' => 'required_completed_quest_id', 
			'label' => 'required_completed_quest_id', 
			'rules' => 'trim|intval'
			), 
		'duration' => array(
			'field' => 'duration', 
			'label' => 'duration', 
			'rules' => 'trim|intval'
			), 
		'completion_requirement_type' => array(
			'field' => 'completion_requirement_type', 
			'label' => 'completion_requirement_type', 
			'rules' => 'trim|intval'
			), 
		'completion_requirement' => array(
			'field' => 'completion_requirement', 
			'label' => 'completion_requirement', 
			'rules' => 'trim|required|xss_clean'
			), 
		);

		// required_answer(text_input)/required_item(item_id)/required_quest(quest_id)/required_objective(objective_id)/required_attribute_value(attribute value)
		public $completion_types = array(
			'0' => 'Input an Answer',    
			'1' => 'Item in inventory',
			'2' => 'Completed Quest',
			'3' => 'Having value of Attribute',
			'4' => 'Being in region'
			);

		public function get_new ()
		{
			$quest = new stdClass();
			
			$quest->name = '';
			$quest->info = '';
			$quest->image = '';
			$quest->reward_id = NONE_ID;
			$quest->autostart = 0;
			$quest->region_id = NONE_ID;
			$quest->required_completed_quest_id = NONE_ID;
			$quest->duration = NONE_ID;
			$quest->completion_requirement_type = 0;
			$quest->completion_requirement = '';

			return $quest;
		}		

		public function create($name,$info,$image,$reward_id,$autostart,$region_id,$required_completed_quest_id,$duration,$completion_requirement_type,$completion_requirement){
			$quest['name'] = $name;
			$quest['info'] = $info;
			$quest['image'] = $image;
			$quest['reward_id'] = $reward_id;
			$quest['autostart'] = $autostart;
			$quest['region_id'] = $region_id;
			$quest['required_completed_quest_id'] = $required_completed_quest_id;
			$quest['duration'] = $duration;
			$quest['completion_requirement_type'] = $completion_requirement_type;
			$quest['completion_requirement'] = $completion_requirement;
			$this->save($quest);
		}

		public function get_for_dropdown(){
			$empty = array(NONE_ID => 'No quest');
			$result = $this->get_array();		
			$result = array_column($result, 'name', 'id');
			$result = $empty + $result;		
			return $result;
		}

		public function get_by_for_dropdown($where, $single = FALSE){
			$empty = array(NONE_ID => 'No quest');
			$result = $this->get_array_by($where, $single = FALSE);		
			$result = array_column($result, 'name', 'id');
			$result = $empty + $result;		
			return $result;
		}

		public function check_completion($quest_id, $answer = NULL){
			$quest = $this->get_by("`id` = '".$quest_id."'");
			return $quest->completion_requirement == $answer;	
		}


		private function generateUniqueCode(){
			$this->load->helper('string'); 		
			$code = random_string('alnum', (config_item('qrcode_length') - 1));
			$exists = $this->get_by('`code` = "'.$code.QR_QUEST.'"', TRUE);
			if(!empty($exists)){
				$code = $this->generateUniqueCode();
			}  		
			return $code.QR_QUEST; 		
		}

		public function save($data, $id = NULL){
			if($id == NULL){
				$data['code'] = $this->generateUniqueCode();
			}	
			parent::save($data, $id);
		}


}