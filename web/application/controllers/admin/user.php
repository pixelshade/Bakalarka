<?php 
/**
* User
*/	
class User extends Admin_Controller
{
	
	function __construct()
	{
		parent::__construct(); 
	}

	public function index(){
		$this->data['users'] = $this->user_m->get();
		$this->data['rights_levels'] = $this->user_m->rights_levels;
		$this->data['subview'] = 'admin/user/index';
		$this->load->view('admin/_layout_main', $this->data);
	}

	public function edit($id = NULL){
		// Fetch a user or set a new one
		if ($id) {
			$this->data['user'] = $this->user_m->get($id);
			count($this->data['user']) || $this->data['errors'][] = 'User could not be found';
		}
		else {
			$this->data['user'] = $this->user_m->get_new();
		}
		
		// Set up the form
		$rules = $this->user_m->rules_admin;
		$id || $rules['password']['rules'] .= '|required';
		$this->form_validation->set_rules($rules);
		$this->data['rights_levels'] = $this->user_m->rights_levels;
		$this->data['default_rights_level'] = $this->user_m->default_rights_level;

		// Process the form
		if ($this->form_validation->run() == TRUE) {
			$data = $this->user_m->array_from_post(array('name', 'email', 'password'));
			$data['password'] = $this->user_m->hash($data['password']);
			$this->user_m->save($data, $id);
			redirect('admin/user');
		}
		
		// Load the view
		$this->data['subview'] = 'admin/user/edit';
		$this->load->view('admin/_layout_main', $this->data);
	}
	public function delete($id){
		$this->user_m->delete($id);
		redirect('admin/user');
	}

	public function login(){
		$dashboard = 'admin/dashboard';
		$this->user_m->loggedin() == FALSE || redirect($dashboard);

		$rules = $this->user_m->rules;
		$this->form_validation->set_rules($rules);
		if($this->form_validation->run() == TRUE){
			// login and redirect
			if($this->user_m->login() == TRUE){
				redirect($dashboard);				
			} else {
				$this->session->set_flashdata('error', 'That email/password doest exist. too bad');
				redirect('admin/user/login', 'refresh');
			}

		}
		$this->data['subview'] = 'admin/user/login';
		$this->load->view('admin/_layout_modal', $this->data);
	}

	public function logout(){
		$this->user_m->logout();
		redirect('admin/user/login');
	}

	public function _unique_email($str){
		$id = $this->uri->segment(4);
		$this->db->where('email', $this->input->post('email'));
		!$id || $this->db->where('id !=', $id);
		$user = $this->user_m->get();

		if(count($user)){
			$this->form_validation->set_message('_unique_email', '%s should be unique');
			return FALSE;
		}

		return TRUE;
	}
}