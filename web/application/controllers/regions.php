<?php
/**
* Region Controller
*/
class Regions extends MY_Controller
{
	
	function __construct()
	{
		parent::__construct();
		$this->load->model('region_model');
		$this->load->helper('url');
	}

	public function index()
	{
		$data['regions'] = $this->region_model->get_regions();
		$data['name'] = 'Regions';

		$this->load->view('include/header', $data);
		$this->load->view('regions/index', $data);
		$this->load->view('include/footer');		
	}

	public function view($id)
	{
		$data['region'] = $this->region_model->get_region($id);

		if (empty($data['region']))
		{
			show_404();			
		}

		//$data['name'] = $data['region']['name'];		
		$this->load->view('include/header', $data);
		$this->load->view('regions/view', $data);
		$this->load->view('include/footer');
	}

	public function create()
	{
		$this->load->helper('form');
		$this->load->library('form_validation');

		$data['name'] = 'Create a regions item';

		$this->form_validation->set_rules('name', 'Name', 'required');
		$this->form_validation->set_rules('lat_start', 'lat_start', 'required');
		$this->form_validation->set_rules('lon_start', 'lon_start', 'required');
		$this->form_validation->set_rules('lat_end', 'lat_end', 'required');		
		
		if ($this->form_validation->run() === FALSE)
		{
			$this->load->view('include/header', $data);
			$this->load->view('regions/create');
			$this->load->view('include/footer');
		}
		else
		{
			$this->region_model->set_region();
			$this->load->view('regions/success');
		}
	}


}


?>