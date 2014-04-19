<?php
/**
* User_m
*/
class User_M extends MY_Model
{
	protected $_table_name = 'users';
	protected $_order_by = 'name';
	// login
	public $rules = array(
		'email' => array(
			'field' => 'email',
			'label' => 'Email',
			'rules' => 'trim|required|valid_email|xss_clean'
			),
		'password' => array(
			'field' => 'password',
			'label' => 'Password',
			'rules' => 'trim|required'
			)
		);
	// normal register
	public $rules_register = array(
		'name' => array(
			'field' => 'name',
			'label' => 'Name',
			'rules' => 'trim|xss_clean'
			),		
		'email' => array(
			'field' => 'email',
			'label' => 'Email',
			'rules' => 'trim|required|valid_email|xss_clean|is_unique[users.email]|callback__unique_email'
			),
		'password' => array(
			'field' => 'password',
			'label' => 'Password',
			'rules' => 'trim|matches[password_confirm]'
			),
		'password_confirm' => array(
			'field' => 'password',
			'label' => 'Password',
			'rules' => 'trim|matches[password]'
			),	
		);

	public $rules_admin = array(
		'name' => array(
			'field' => 'name',
			'label' => 'Name',
			'rules' => 'trim|required|xss_clean'
			),		
		'email' => array(
			'field' => 'email',
			'label' => 'Email',
			'rules' => 'trim|required|valid_email|xss_clean|callback__unique_email'
			),
		'password' => array(
			'field' => 'password',
			'label' => 'Password',
			'rules' => 'trim|matches[password_confirm]'
			),
		'password_confirm' => array(
			'field' => 'password',
			'label' => 'Password',
			'rules' => 'trim|matches[password]'
			),
		'rights_level' => array(
			'field' => 'rights_level',
			'label' => 'rights_level',
			'rules' => 'trim|intval'
			)
		);
	
	function __construct()
	{
		parent::__construct();
	}


	public $rights_levels = array(0 => 'admin', 1 => 'content_creator', 2 => 'normal_user');
	public $default_rights_level = 2;

	public function login(){
		$user = $this->get_by(array(
			'email' => $this->input->post('email'),
			'password' => $this->hash($this->input->post('password')),
			), TRUE);

		if(count($user)){
			// Log in user
			$data = array(
				'name' => $user->name,
				'email' => $user->email,
				'id' => $user->id,
				'loggedin' => TRUE,
				);
			$this->session->set_userdata($data);			
		}
		return $user;
	}
	public function logout(){
		$this->session->sess_destroy();
	}
	public function loggedin(){
		return (bool) $this->session->userdata('loggedin');
	}
	public function hash($string){
		return hash('sha512', $string . config_item('encryption_key'));
	}	

	public function get_new(){
		$user = new stdClass();
		$user->name = '';
		$user->email = '';
		$user->password = '';
		$user->rights_level = $this->default_rights_level;
		return $user;
	}

	public function get_user_id(){
		return $this->session->userdata('id');;
	}

	public function get_for_dropdown(){
		$empty = array(NONE_ID => 'No user');
		$result = $this->get_array();		
		$result = array_column($result, 'name', 'id');
		$result = $empty + $result;		
		return $result;
	}

}
?>