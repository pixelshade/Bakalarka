<?php
/**
* Content_Files_model
*/

class Content_Files_Model extends CI_Model {

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
        if (!$this->db->where('id', $file_id)->delete('content_files'))
        {
            return FALSE;
        }
        unlink(APPPATH."app_content/" . $file->filename);    
        return TRUE;
    }

    public function get_file($file_id = NULL)
    {
        return $this->db->select()
        ->from('content_files')
        ->where('id', $file_id)
        ->get()
        ->row();
    }

   public function getAll(){    
        $query = $this->db->get('content_files');
        return $query->result_array();
    }

}

