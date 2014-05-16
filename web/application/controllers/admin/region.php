<?php
class Region extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('region_m');
		// $this->load->model('region_movement_m');
	}

	public function index ()
	{
		// Fetch all regions
		$this->data['regions'] = $this->region_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/region/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		//Fetch images
		$this->load->model('content_files_model');
		$this->data['images'] = $this->content_files_model->get_for_dropdown();


		// Fetch a region or set a new one
		if ($id) {
			$this->data['region'] = $this->region_m->get($id);
			count($this->data['region']) || $this->data['errors'][] = 'region could not be found';
			// $id_movement = $this->region_movement_m->get_by('`region_id` ="'.$id.'"',TRUE);
			// if(empty($id_movement)){
			// 	$id_movement = null;
			// } else {
			// 	$id_movement = $id_movement->id;
			// }
		}
		else {
			$this->data['region'] = $this->region_m->get_new();
			// $id_movement = null;
		}
		
		// Set up the form
		$rules = $this->region_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->region_m->array_from_post(array(
				'name',
				'info',
				'image',
				'lat_start',
				'lon_start',
				'lat_end',
				'lon_end',	
				'movement',			
				));
			$this->region_m->save($data, $id);

			// $data = $this->region_m->array_from_post(array(
			// 		'movement'	
			// 	));
			

			// $this->region_movement_m->save($data, $id_movement);
			


			redirect('admin/region');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/region/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->region_m->delete($id);
		redirect('admin/region');
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