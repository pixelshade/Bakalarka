<h3><?php echo empty($user_item->id) ? 'Add a new user_item' : 'Edit user_item ' . $user_item->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>char_id</td>
		<td><?php echo form_input('char_id', set_value('char_id', $user_item->char_id), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>item_id</td>
		<td><?php echo form_input('item_id', set_value('item_id', $user_item->item_id), 'class="form-control"'); ?></td>		
	</tr>
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
