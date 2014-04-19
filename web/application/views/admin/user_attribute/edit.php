<h3><?php echo empty($user_attribute->id) ? 'Add a new user_attribute' : 'Edit user_attribute '; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
<tr>
		<td>char_id</td>
		<td><?php echo form_dropdown('char_id', $chars, $user_attribute->char_id, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>
		<td>attribute_id</td>
		<td><?php echo form_dropdown('attribute_id', $attributeNames, $user_attribute->attribute_id, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>
		<td>amount</td>
		<td><?php echo form_input('amount', set_value('amount', $user_attribute->amount), 'class="form-control"'); ?></td>		
	</tr>
	
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
