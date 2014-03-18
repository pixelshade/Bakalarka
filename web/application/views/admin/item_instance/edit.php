<h3><?php echo empty($item_instance->id) ? 'Add a new item_instance' : 'Edit item_instance ' . $item_instance->item_definition_id; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>


<table class="table">
	<tr>
		<td>item_definition_id</td>
		<td><?php echo form_dropdown('item_definition_id', $item_names ,$item_instance->item_definition_id, 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>latitude</td>
		<td><?php echo form_input('latitude', set_value('latitude', $item_instance->latitude), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>longtitude</td>
		<td><?php echo form_input('longtitude',set_value('longtitude', $item_instance->longtitude), 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>
		<td>amount</td>
		<td><?php echo form_input('amount', set_value('amount', $item_instance->amount), 'class="form-control"'); ?></td>		
	</tr>		
	<tr>		
		<td colspan="2"><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
	</tr>
</table>
<?php echo form_close();?>
