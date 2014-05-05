<?php
class User_quest extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_quest_m');
		$this->load->model('user_m');
		$this->load->model('quest_m');
		$this->data['chars'] = $this->user_m->get_for_dropdown();	
		$this->data['quests'] = $this->quest_m->get_for_dropdown();	
	}

	public function index ()
	{
		// Fetch all user_quests
		$this->data['user_quests'] = $this->user_quest_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/user_quest/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		
		// Fetch a user_quest or set a new one
		if ($id) {
			$this->data['user_quest'] = $this->user_quest_m->get($id);
			count($this->data['user_quest']) || $this->data['errors'][] = 'user_quest could not be found';
		}
		else {
			$this->data['user_quest'] = $this->user_quest_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_quest_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_quest_m->array_from_post(array(
				'char_id',
				'quest_id',
				'time_accepted',
				'completed',				
				));
			$this->user_quest_m->save($data, $id);
			redirect('admin/user_quest');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user_quest/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->user_quest_m->delete($id);
		redirect('admin/user_quest');
	}
}