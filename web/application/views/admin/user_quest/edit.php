<h3><?php echo empty($user_quest->id) ? 'Add a new user_quest' : 'Edit user_quest ' ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_input('char_id', set_value('char_id', $user_quest->char_id), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>quest_id</td>
		<td><?php echo form_input('quest_id', set_value('quest_id', $user_quest->quest_id), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>time_accepted</td>
		<td><?php echo form_input('time_accepted', set_value('time_accepted', $user_quest->time_accepted), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>completed</td>
		<td><?php echo form_boolean_select('completed',$user_quest->completed); ?>	</td>
	</tr>	
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
