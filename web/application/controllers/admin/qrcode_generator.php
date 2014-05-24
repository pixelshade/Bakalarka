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
		// TODO nalinkovat na web/clienta a spojit s kodom 
		//config_item('client_download_url').
		$params['data'] = $string;
		$params['level'] = 'H';
		$params['size'] = 10;

		$im = $this->ciqrcode->generateImage($params);			
				
		if ($im !== false) {
				
			
			$white = imagecolorallocate($im, 255, 255, 255);
			$width = imagesx($im);
			$height = imagesy($im);
			$center_w = $width/2;
			$center_h = $height/2;
			$logo_size = $width * 0.25;
			$half_size = $logo_size/2;

			if(config_item('add_logo_to_qrcode')){
				$file_path = config_item('qrlogo_filename');			
				// Load
				$image_path = 	"./app_content/".$file_path;		
				$im_logo = imagecreatefrompng($image_path);
				$logo_w = imagesx($im_logo);
				$logo_h = imagesy($im_logo);
				// Resize			
				imagecopyresized($im, $im_logo, $center_w - $half_size, $center_h - $half_size, 0,0 ,$logo_size ,$logo_size, $logo_w, $logo_h);
				imagedestroy($im_logo);
			}			
			imagepng($im);
			imagedestroy($im);
			

		}
		else {
			echo 'An error occurred.';
		}
	}


}