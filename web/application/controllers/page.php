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

	public function index(){
	
	}
}