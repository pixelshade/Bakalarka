<h3><?php echo empty($user_attribute->id) ? 'Add a new user_attribute' : 'Edit user_attribute ' . $user_attribute->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_input('char_id', set_value('char_id', $user_attribute->char_id), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>attribute_id</td>
		<td><?php echo form_input('attribute_id', set_value('attribute_id', $user_attribute->attribute_id), 'class="form-control"'); ?></td>		
	</tr>
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
