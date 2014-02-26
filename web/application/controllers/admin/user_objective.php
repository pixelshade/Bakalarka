<?php
class User_objective extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_objective_m');
	}

	public function index ()
	{
		// Fetch all user_objectives
		$this->data['user_objectives'] = $this->user_objective_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/user_objective/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		
		// Fetch a user_objective or set a new one
		if ($id) {
			$this->data['user_objective'] = $this->user_objective_m->get($id);
			count($this->data['user_objective']) || $this->data['errors'][] = 'user_objective could not be found';
		}
		else {
			$this->data['user_objective'] = $this->user_objective_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_objective_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_objective_m->array_from_post(array(
				'char_id',
				'objective_id',
				'completed',				
				));
			$this->user_objective_m->save($data, $id);
			redirect('admin/user_objective');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user_objective/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->user_objective_m->delete($id);
		redirect('admin/user_objective');
	}
}