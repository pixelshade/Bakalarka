<?php $this->load->view('include/header.php'); ?>

<div class="modal show">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div> -->
		<?php $this->load->view($subview); //subview is set in controller ?> 



      <div class="modal-footer">       
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<?php $this->load->view('include/footer.php'); ?>