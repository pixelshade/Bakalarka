<h3><?php echo empty($reward->id) ? 'Add a new reward' : 'Edit reward '; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>name</td>
		<td><?php echo form_input('name', set_value('name', $reward->name), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>item_definition_id</td>
		<td><?php echo form_dropdown('item_definition_id', $item_definitions ,$reward->item_definition_id, 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>item_amount</td>
		<td><?php echo form_input('item_amount', set_value('item_amount', $reward->item_amount), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>attribute_id</td>
		<td><?php echo form_dropdown('attribute_id', $attributes, $reward->attribute_id, 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>attribute_amount</td>
		<td><?php echo form_input('attribute_amount',set_value('attribute_amount', $reward->attribute_amount), 'class="form-control"'); ?>	</td>
	</tr>	
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
