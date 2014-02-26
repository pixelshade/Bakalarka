<h3><?php echo empty($user_objective->id) ? 'Add a new user_objective' : 'Edit user_objective ' . $user_objective->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_input('char_id', set_value('char_id', $user_objective->char_id), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>objective_id</td>
		<td><?php echo form_input('objective_id', set_value('objective_id', $user_objective->objective_id), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>completed</td>
		<td><?php echo form_boolean_select('completed',$user_objective->completed); ?>	</td>
	</tr>	
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
