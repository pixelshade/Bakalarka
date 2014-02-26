<h3><?php echo empty($item_definition->id) ? 'Add a new item_definition' : 'Edit item_definition ' . $item_definition->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>


<table class="table">
	<tr>
		<td>name</td>
		<td><?php echo form_input('name', set_value('name', $item_definition->name), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>info</td>
		<td><?php echo form_input('info', set_value('info', $item_definition->info), 'class="tinymce"'); ?></td>
	</tr>
	<tr>
		<td>image</td>
		<td><?php echo form_dropdown('image',$images,  $item_definition->image, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>		
		<td colspan="2"><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
	</tr>
</table>
<?php echo form_close();?>
