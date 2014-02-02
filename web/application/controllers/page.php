<?php
/**
*  Page
*/
class Page extends Frontend_Controller
{
	
	function __construct()
	{
		parent::__construct();
		$this->load->model('page_m');
	}

	public function index($id = NULL){
		dump($id);
		$this->load->view('_main_layout.php', $this->data);
	
	}
}