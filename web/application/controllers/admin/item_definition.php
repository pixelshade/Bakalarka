<?php
class Item_definition extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('item_definition_m');
	}

	public function index ()
	{
		// Fetch all item_definitions
		$this->data['item_definitions'] = $this->item_definition_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/item_definition/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		//Fetch images
		$this->load->model('content_files_model');
		$this->data['images'] = $this->content_files_model->get_all_for_dropdown();


		// Fetch a item_definition or set a new one
		if ($id) {
			$this->data['item_definition'] = $this->item_definition_m->get($id);
			count($this->data['item_definition']) || $this->data['errors'][] = 'item_definition could not be found';
		}
		else {
			$this->data['item_definition'] = $this->item_definition_m->get_new();
		}
		
		// Set up the form
		$rules = $this->item_definition_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->item_definition_m->array_from_post(array(
				'name',
				'info',
				'image'				
				));
			$this->item_definition_m->save($data, $id);
			redirect('admin/item_definition');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/item_definition/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->item_definition_m->delete($id);
		redirect('admin/item_definition');
	}



}