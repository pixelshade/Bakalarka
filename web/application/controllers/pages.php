<?php

class Pages extends Frontend_Controller {

	public function view($page = 'home')
	{

		if ( ! file_exists('application/views/pages/'.$page.'.php'))
		{
		// Whoops, we don't have a page for that!			
			show_404();		
			
		}
		
	$data['title'] = ucfirst($page); // Capitalize the first letter	
	$this->load->helper('url');
	$this->load->view('include/header', $data);
	$this->load->view('pages/'.$page, $data);
	$this->load->view('include/footer', $data);

}
} 