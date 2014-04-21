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
		$this->data['page'] = $this->page_m->get_by('`slug` = "'.$id.'"', TRUE);
		$this->data['subview'] = '_layout_page.php';
		$this->load->view('_layout_main.php', $this->data);
	
	}
}