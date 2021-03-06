<?php
class Cron_task_m extends MY_Model
{
	protected $_table_name = 'cron_tasks';
	protected $_order_by = 'name, id desc';
	public $rules = array(
		'name' => array(
			'field' => 'name', 
			'label' => 'Name', 
			'rules' => 'trim|required|max_length[100]|xss_clean'
			), 
		'json_params' => array(
			'field' => 'json_params', 
			'label' => 'json_params', 
			'rules' => 'trim|required'
			),	
		'task_type' => array(
			'field' => 'task_type', 
			'label' => 'task_type', 
			'rules' => 'trim|required|intval'
			),
		'active' => array(
			'field' => 'active', 
			'label' => 'active', 
			'rules' => 'trim|required|intval'
			),
		'time_to_run' => array(
			'field' => 'time_to_run', 
			'label' => 'time_to_run', 
			'rules' => 'trim|required|intval'
			)

		
		);

	public function get_new ()
	{
		$cron_task = new stdClass();

		$cron_task->name = '';
		$cron_task->json_params = '';		
		$cron_task->task_type = '';	
		$cron_task->active = 0;	
		$cron_task->time_to_run = '';
		return $cron_task;
	}

	public $task_types = array(		
		0 => 'Create region', 
		1 => 'Create quest', 		
		);

	public function create($name, $json_params, $task_type, $active, $time_to_run)
	{
		
	}	

	public function setup($controler){
		// $output = shell_exec('crontab -l');
		// echo $output;
		// file_put_contents('/tmp/crontab.txt', '* * * * * php '.$controler.' run '.PHP_EOL);
		// file_put_contents('/tmp/crontab.txt', '* * * * * php '.$controler.' /run '.PHP_EOL);
		// echo exec('crontab /tmp/crontab.txt');
		$cmd= 'php '.$controler;		
		echo exec($cmd);		
		echo "<hr>";
		echo $cmd;

	}


	public function delete_all(){
		echo exec('crontab -r');	
	}

	public function run(){	
		// echo exec('whereis php');		
		
		$cont = file_get_contents('/tmp/count.txt');
		$cont .= date("H:i:s")."<br/>\n".PHP_EOL;
		echo $cont;		
		file_put_contents('/tmp/count.txt', $cont);		
	}



	public function run_tasks(){

		$actual_time = date('i');
		$tasks = $this->get_by('`active` = "1" AND `time_to_run` = "'.$actual_time.'"');
		if(!empty($tasks)){
			foreach ($tasks as $task) {				
				$task_type = $task->task_type;		
				$params = json_decode($task->json_params);
				if($this->task_type == 2){
					
					$region['lat_start'] = $params['lat_start'];
					$region['lon_start'] = $params['lon_start'];
					$region['lat_end'] = $params['lat_end'];
					$region['lon_end'] = $params['lon_end'];
					$region['region_id'] = $params['region_id'];
					$this->region_m->change_location($params['lat_start'],$params['lon_start'],$params['lat_end'],$params['lon_end'],$params['region_id']);
					// $this->region_m->save($region, $params->region_id);

				} elseif($this->task_type == 0){
					$region->name = $params['name'];
					$region->info = $params['info'];
					$region->image = $params['image'];
					$region->lat_start =  $params['lat_start'];
					$region->lon_start =  $params['lon_start'];
					$region->lat_end =  $params['lat_end'];
					$region->lon_end =  $params['lon_end'];
					
					$this->region_m->save($region);

				} elseif ($this->task_type == 1) {
					$quest->name = $params['name'];					
					$quest->info = $params['info'];
					$quest->image = $params['image'];
					$quest->reward_id = $params['reward_id'];
					$quest->autostart = $params['autostart'];
					$quest->region_id = $params['region_id'];
					$quest->required_completed_quest_id = $params['required_completed_quest_id'];
					$quest->duration = $params['duration'];
					$quest->completion_requirement_type = $params['completion_requirement_type'];
					$quest->completion_requirement = $params['completion_requirement'];
					
					$this->quest_m->save($quest);
				}
			}
		}
	}

	

}