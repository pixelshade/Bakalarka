<?php
class Reward extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('reward_m');
		$this->load->model('attribute_m');
		$this->load->model('item_definition_m');
	}

	public function index ()
	{
		// Fetch all rewards
		$this->data['rewards'] = $this->reward_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/reward/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{	
		$this->data['attributes'] = $this->attribute_m->get_for_dropdown();
		$this->data['item_definitions'] = $this->item_definition_m->get_for_dropdown();
		// Fetch a reward or set a new one
		if ($id) {
			$this->data['reward'] = $this->reward_m->get($id);
			count($this->data['reward']) || $this->data['errors'][] = 'reward could not be found';
		}
		else {
			$this->data['reward'] = $this->reward_m->get_new();			
		}
		
		// Set up the form
		$rules = $this->reward_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->reward_m->array_from_post(array(
				'name',
				'item_definition_id',
				'item_amount',
				'attribute_id',
				'attribute_amount'				
				));
			$this->reward_m->save($data, $id);
			redirect('admin/reward');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/reward/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->reward_m->delete($id);
		redirect('admin/reward');
	}

}