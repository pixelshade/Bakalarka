<?php
class User_qrscanned extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_qrscanned_m');
		$this->load->model('user_m');	
	}

	public function index ()
	{
		// Fetch all user_qrscanneds
		$this->data['user_qrscanneds'] = $this->user_qrscanned_m->get();
		$this->data['chars'] = $this->user_m->get_for_dropdown();
		
		// Load view
		$this->data['subview'] = 'admin/user_qrscanned/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		
		// Fetch a user_qrscanned or set a new one
		if ($id) {
			$this->data['user_qrscanned'] = $this->user_qrscanned_m->get($id);
			count($this->data['user_qrscanned']) || $this->data['errors'][] = 'user_qrscanned could not be found';
		}
		else {
			$this->data['user_qrscanned'] = $this->user_qrscanned_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_qrscanned_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_qrscanned_m->array_from_post(array(
				'char_id',
				'qrscanned',							
				));
			
			$this->user_qrscanned_m->save($data, $id);
			redirect('admin/user_qrscanned');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user_qrscanned/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->user_qrscanned_m->delete($id);
		redirect('admin/user_qrscanned');
	}
}