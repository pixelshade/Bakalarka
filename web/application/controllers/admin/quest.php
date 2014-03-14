<?php
class Quest extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('quest_m');
		$this->load->model('content_files_model');
		$this->load->model('region_m');
		$this->load->model('reward_m');		

		//Fetch images
		$this->data['imageNames'] = $this->content_files_model->get_for_dropdown();

		//Fetch regions
		$this->data['regionNames'] = $this->region_m->get_for_dropdown();				

		// Fetch rewards
		$this->data['rewardNames'] = $this->reward_m->get_for_dropdown();

		// Fetch questNames
		$this->data['questNames'] = $this->quest_m->get_for_dropdown();

		$this->data['completion_types'] = $this->quest_m->completion_types;

	}

	public function index ()
	{
		// Fetch all quests
		$this->data['quests'] = $this->quest_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/quest/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{

		// Fetch a quest or set a new one
		if ($id) {
			$this->data['quest'] = $this->quest_m->get($id);
			count($this->data['quest']) || $this->data['errors'][] = 'quest could not be found';
			// Fetch quests other without this
			$where = "id != '".$id."'";
			$this->data['quests'] = $this->quest_m->get_by_for_dropdown($where);
			// Fetch required quests
		}
		else {
			$this->data['quest'] = $this->quest_m->get_new();
		
			// Fetch quests
			$this->data['quests'] = $this->quest_m->get_for_dropdown();
		}

		// Set up the form
		$rules = $this->quest_m->rules;
		$this->form_validation->set_rules($rules);

		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->quest_m->array_from_post(array(
				'code',
				'name',
				'info',
				'image',
				'reward_id',
				'autostart',
				'region_id',
				'required_completed_quest_id',
				'completion_requirement_type',
				'completion_requirement',
				));
			$this->quest_m->save($data, $id);
			redirect('admin/quest');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/quest/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->quest_m->delete($id);
		redirect('admin/quest');
	}

}