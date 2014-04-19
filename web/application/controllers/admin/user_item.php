<?php
class User_item extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_item_m');
		$this->load->model('user_m');
		$this->load->model('item_definition_m');
		$this->data['chars'] = $this->user_m->get_for_dropdown();		
		// Fetch items
		$this->data['itemNames'] = $this->item_definition_m->get_for_dropdown();
	}

	public function index ()
	{
		// Fetch all user_items
		$this->data['user_items'] = $this->user_item_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/user_item/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		
		// Fetch a user_item or set a new one
		if ($id) {
			$this->data['user_item'] = $this->user_item_m->get($id);
			count($this->data['user_item']) || $this->data['errors'][] = 'user_item could not be found';
		}
		else {
			$this->data['user_item'] = $this->user_item_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_item_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_item_m->array_from_post(array(
				'char_id',
				'item_id',							
				'amount',							
				));
			$this->user_item_m->save($data, $id);
			redirect('admin/user_item');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user_item/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->user_item_m->delete($id);
		redirect('admin/user_item');
	}
}