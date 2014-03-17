<?php
class Qrcode_Generator extends Admin_Controller
{

	public function __construct(){
		parent::__construct();
		$this->load->library('ciqrcode');	

	}

	public function index(){
		$this->load->model('quest_m');				
		$this->load->model('item_definition_m');		

	}




	public function get($string){
		header("Content-Type: image/png");		
		$params['data'] = $string;
		$params['level'] = 'H';
		$params['size'] = 10;
		$this->ciqrcode->generate($params);	
	}
}