<?php
class Item_instance extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('item_instance_m');
		$this->load->model('item_definition_m');		
	}

	public function index ()
	{
		// Fetch all item_instances
		$this->data['item_instances'] = $this->item_instance_m->get();
		// Fetch all item definitions
		$this->data['item_names'] = $this->item_definition_m->get_for_dropdown();


		
		// Load view
		$this->data['subview'] = 'admin/item_instance/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		// Fetch id of user editing/creating item instance
		$user_id = $this->user_m->get_user_id();
		// Fetch all item definitions
		$this->data['item_names'] = $this->item_definition_m->get_for_dropdown();


		// Fetch a item_instance or set a new one
		if ($id) {
			$this->data['item_instance'] = $this->item_instance_m->get($id);
			count($this->data['item_instance']) || $this->data['errors'][] = 'item_instance could not be found';
		}
		else {
			$this->data['item_instance'] = $this->item_instance_m->get_new();
		}
		
		// Set up the form
		$rules = $this->item_instance_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->item_instance_m->array_from_post(array(
				'item_definition_id',
				'latitude',
				'longtitude',
				'amount',				
				));
			if($data['latitude']=="" || $data['longtitude']==""){
				$data['latitude'] = null;
				$data['longtitude'] = null;
			}
			$data['added_by_user'] = $user_id;
			$this->item_instance_m->save($data, $id);
			redirect('admin/item_instance');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/item_instance/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->item_instance_m->delete($id);
		redirect('admin/item_instance');
	}



}