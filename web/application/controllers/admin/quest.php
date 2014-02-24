<?php
class Quest extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('quest_m');
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
		//Fetch images
		$this->load->model('content_files_model');
		$this->data['images'] = $this->content_files_model->get_all_for_dropdown();


		// Fetch a quest or set a new one
		if ($id) {
			$this->data['quest'] = $this->quest_m->get($id);
			count($this->data['quest']) || $this->data['errors'][] = 'quest could not be found';
		}
		else {
			$this->data['quest'] = $this->quest_m->get_new();
		}
		
		// Set up the form
		$rules = $this->quest_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->quest_m->array_from_post(array(
				'name',
				'info',
				'image',
				'lat_start',
				'lon_start',
				'lat_end',
				'lon_end'
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


	public function _is_coordinate ($str)
	{
		if((bool)preg_match('/^[\-+]?[0-9]+\.[0-9]+$/', $str)){
			$this->form_validation->set_message('_is_coordinate', '%s should be coordinate');
			return FALSE;
		}
		return TRUE;		
	}

}