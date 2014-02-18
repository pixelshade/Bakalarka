<?php
/**
* Content_Files_model
*/

class Content_Files_Model extends CI_Model {
    public $content_dir = "app_content/";

    public function insert_file($filename)
    {
        $data = array(
            'filename'      => $filename,            
            );
        $this->db->insert('content_files', $data);
        return $this->db->insert_id();
    }

    public function delete_file($file_id)
    {
        $file = $this->get_file($file_id);  
        if(count($file) != 0) {     
            $file_url_to_del = $this->content_dir . $file->filename;
            if (!$this->db->where('id', $file_id)->delete('content_files'))
            {
                return FALSE;
            }
            if(unlink($file_url_to_del)){            
                return TRUE;           
            }
        }
        return FALSE;
        
    }

    public function get_file($file_id = NULL)
    {
        return $this->db->select()
        ->from('content_files')
        ->where('id', $file_id)
        ->get()
        ->row();
    }

    public function get_all_for_dropdown(){
        $results = $this->db->get('content_files')->result_array();
        $empty = array("" => "No image");
        if (count($results) > 0) {                                            
            foreach ($results as $value) {
                $filenames[$value['filename']] = $value['filename'];               
            }                          
            $results = array_merge($empty,$filenames);
        } else {
            $results = $empty;
        }
        return $results;
    }

    public function get_all(){ 
        return $this->db->get('content_files')->result_array(); 
    }

     public function get_all_names(){         
         $result = $this->db->select('filename')->get('content_files')->result_array(); 
         foreach ($result as $name) {
             $names[] = $name['filename'];
         }
         return $names;
    }

}

