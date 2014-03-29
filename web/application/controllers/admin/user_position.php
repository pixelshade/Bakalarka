<?php
class User_position extends Admin_Controller
{

	public function __construct ()
	{
		parent::__construct();
		$this->load->model('user_position_m');
	}

	public function index ()
	{
		// Fetch all user_positions
		$this->data['user_positions'] = $this->user_position_m->get();
		
		// Load view
		$this->data['subview'] = 'admin/user_position/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function show($char_id = NULL){
		if($char_id != NULL){
			$this->data['user_positions'] = $this->user_position_m->get_by("`char_id` = '".$char_id."'");

		
		}	
		// Load view
			$this->data['subview'] = 'admin/user_position/show';
			$this->load->view('admin/_layout_main', $this->data);	

	}

	public function delete ($id)
	{
		$this->user_position_m->delete($id);
		redirect('admin/user_position');
	}

	public function delete_all ()
	{
		$this->user_position_m->delete_all();
		redirect('admin/user_position');
	}
}