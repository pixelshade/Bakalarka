<?php
class Attribute extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('attribute_m');
	}

	public function index ()
	{
		// Fetch all attributes
		$this->data['attributes'] = $this->attribute_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/attribute/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		//Fetch images
		$this->load->model('content_files_model');
		$this->data['images'] = $this->content_files_model->get_for_dropdown();


		// Fetch a attribute or set a new one
		if ($id) {
			$this->data['attribute'] = $this->attribute_m->get($id);
			count($this->data['attribute']) || $this->data['errors'][] = 'attribute could not be found';
		}
		else {
			$this->data['attribute'] = $this->attribute_m->get_new();
		}
		
		// Set up the form
		$rules = $this->attribute_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->attribute_m->array_from_post(array(
				'name',
				'info',
				'image'				
				));
			$this->attribute_m->save($data, $id);
			redirect('admin/attribute');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/attribute/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$deleted = $this->attribute_m->delete($id);
		if($deleted){
			$this->load->model('user_attribute_m');
			$this->user_attribute_m->delete_all_with_attribute_id($id);
		}
		redirect('admin/attribute');
	}
}