<?php
class Cron_task extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('cron_task_m');

	}

	public function index ()
	{
		// Fetch all cron_tasks
		$this->data['cron_tasks'] = $this->cron_task_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/cron_task/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit ($id = NULL)
	{
		//Fetch images
		$this->load->model('content_files_model');
		$this->load->model('region_m');
		$this->data['images'] = $this->content_files_model->get_for_dropdown();
		$this->data['regions'] = $this->region_m->get_for_dropdown();


		// Fetch a cron_task or set a new one
		if ($id) {
			$this->data['cron_task'] = $this->cron_task_m->get($id);
			count($this->data['cron_task']) || $this->data['errors'][] = 'cron_task could not be found';
		}
		else {
			$this->data['cron_task'] = $this->cron_task_m->get_new();
		}
		
		// Set up the form
		$rules = $this->cron_task_m->rules;
		$this->form_validation->set_rules($rules);
		
		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->cron_task_m->array_from_post(array(
				'name',
				'json_params',
				'task_type',				
				'active',	
				));
			$this->cron_task_m->save($data, $id);
			redirect('admin/cron_task');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/cron_task/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function delete ($id)
	{
		$this->cron_task_m->delete($id);
		redirect('admin/cron_task');
	}


	public function _is_coordinate ($str)
	{
		if((bool)preg_match('/^[\-+]?[0-9]+\.[0-9]+$/', $str)){
			$this->form_validation->set_message('_is_coordinate', '%s should be coordinate');
			return FALSE;
		}
		return TRUE;		
	}

	private function move_region($lat_start,$lat_start,$lat_start,$lat_start, $region_id){
		$this->load->model('region_m');
		$this->region_m->change_location($lat_start,$lat_start,$lat_start,$lat_start, $region_id);
	}


	public function run_cron(){
		$this->cron_task_m->run_tasks();
	}

}