<?php
class User_attribute extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_attribute_m');
	}

	public function index ()
	{
		// Fetch all user_attributes
		$this->data['user_attributes'] = $this->user_attribute_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/user_attribute/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		
		// Fetch a user_attribute or set a new one
		if ($id) {
			$this->data['user_attribute'] = $this->user_attribute_m->get($id);
			count($this->data['user_attribute']) || $this->data['errors'][] = 'user_attribute could not be found';
		}
		else {
			$this->data['user_attribute'] = $this->user_attribute_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_attribute_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_attribute_m->array_from_post(array(
				'char_id',
				'attribute_id',							
				));
			$this->user_attribute_m->save($data, $id);
			redirect('admin/user_attribute');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user_attribute/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->user_attribute_m->delete($id);
		redirect('admin/user_attribute');
	}
}