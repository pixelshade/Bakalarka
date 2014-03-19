<?php
class User_position_m extends MY_Model
{
	protected $_table_name = 'user_position';
	protected $_order_by = 'time desc';
	public $rules = array(
		'char_id' => array(
			'field' => 'char_id', 
			'label' => 'char_id', 
			'rules' => 'intval|required'
			), 
		'latitude' => array(
			'field' => 'latitude', 
			'label' => 'latitude', 
			'rules' => 'intval|required'
			),
		'longtitude' => array(
			'field' => 'longtitude', 
			'label' => 'longtitude', 
			'rules' => 'intval|required'
			),
		'time' => array(
			'field' => 'time', 
			'label' => 'tiem user was at the position', 
			'rules' => 'trim|exact_length[19]|xss_clean'
		),
		);

	public function get_new ()
	{
		$user_position = new stdClass();

		$user_position->char_id = NONE_ID;
		$user_position->latitude = 0;
		$user_position->longtitude = 0;
		$user_position->time = date('Y-m-d H:i:s');

		return $user_position;
	}

	public function save($char_id = NONE_ID, $latitude = 0, $longtitude = 0){
		$data['char_id'] = $char_id;
		$data['latitude'] = $latitude;
		$data['longtitude'] = $longtitude;
		$data['time'] = date('Y-m-d H:i:s');
		parent::save($data);
	}

}