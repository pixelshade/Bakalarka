<h3><?php echo empty($user_item->id) ? 'Add a new user_item' : 'Edit user_item '; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_dropdown('char_id', $chars, $user_item->char_id, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>
		<td>item_id</td>
		<td><?php echo form_dropdown('item_id', $itemNames, $user_item->item_id, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>
		<td>amount</td>
		<td><?php echo form_input('amount', set_value('amount', $user_item->amount), 'class="form-control"'); ?></td>		
	</tr>
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
