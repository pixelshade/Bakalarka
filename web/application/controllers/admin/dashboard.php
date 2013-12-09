<?php

/**
* Dashboard
*/
class Dashboard extends Admin_Controller
{
	
	function __construct()
	{
		parent::__construct();
	}

	function index(){
		$this->load->view('_layout_main', $this->data);
	}

	function modal(){
		$this->load->view('_layout_modal', $this->data);
	}
}