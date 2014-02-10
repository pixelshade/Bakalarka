<?php

class Content_file extends Admin_Controller 
{
    public function __construct()
    {
        parent::__construct();
        $this->load->model('content_files_model');
        $this->data['error'] = "";       
    }

    public function index($msg = NULL)
    {
        // Fetch all content files
        $this->data['content_files'] = $this->content_files_model->getAll();
        $this->data['error'] = $msg;
        // Load view
        $this->data['subview'] = 'admin/content_file/upload';
        $this->load->view('admin/_layout_main', $this->data);
    }

    public function upload_file()
    {
        $status = "";
        $msg = "";
        $file_element_name = 'userfile';


        $this->form_validation->set_rules($file_element_name,'File', 'required|is_unique[content_files.filename]');

        if ($this->form_validation->run() == TRUE)
        {
            $status = "error";
            $this->data['error'] = "There is a file or somtheing fuck";
        }
        else
        {
            $config['upload_path'] = APPPATH."app_content/";//$this->content_files_model->upload_path;
            $config['allowed_types'] = 'gif|jpg|png';
            $config['max_size'] = 1024 * 8;
           // $config['encrypt_name'] = TRUE;

            $this->load->library('upload', $config);

            if (!$this->upload->do_upload($file_element_name))
            {
                $status = 'error';
                $this->data['error'] = $this->upload->display_errors('', '');
            }
            else
            {
                $data = $this->upload->data();
                $file_id = $this->content_files_model->insert_file($data['file_name']);
                if($file_id)
                {
                    $status = "success";
                    $this->data['error'] = "File successfully uploaded";
                }
                else
                {
                    unlink($data['full_path']);
                    $status = "error";
                    $this->data['error'] = "Something went wrong whe`n saving the file, please try again.";
                }
            }
            @unlink($_FILES[$file_element_name]);
        }
       // echo json_encode(array('status' => $status, 'msg' => $this->data['error']));     
        redirect('admin/content_file/index/'. $this->data['error']);
    }

    public function delete_file($file_id)
    {
        if ($this->content_files_model->delete_file($file_id))
        {
            $status = 'success';
            $this->data['error'] = 'File successfully deleted';
        }
        else
        {
            $status = 'error';
            $this->data['error'] = 'Something went wrong when deleteing the file, please try again';
        }
       // echo json_encode(array('status' => $status, 'msg' => $this->data['error']));
        redirect('admin/content_file/index/'. $this->data['error']);
    }

}