<h3><?php echo empty($user_qrscanned->id) ? 'Add a new user_qrscanned' : 'Edit user_qrscanned ' ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_input('char_id', set_value('char_id', $user_qrscanned->char_id), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>qrscanned</td>
		<td><?php echo form_input('qrscanned', set_value('qrscanned', $user_qrscanned->qrscanned), 'class="form-control"'); ?></td>		
	</tr>
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
