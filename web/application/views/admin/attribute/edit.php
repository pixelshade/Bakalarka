<h3><?php echo empty($attribute->id) ? 'Add a new attribute' : 'Edit attribute ' . $attribute->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>


<table class="table">
	<tr>
		<td>name</td>
		<td><?php echo form_input('name', set_value('name', $attribute->name), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>info</td>
		<td><?php echo form_input('info', set_value('info', $attribute->info), 'class="tinymce"'); ?></td>
	</tr>
	<tr>
		<td>image</td>
		<td><?php echo form_dropdown('image',$images,  $attribute->image, 'class="form-control"'); ?>	</td>
	</tr>	
	<tr>		
		<td colspan="2"><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
	</tr>
</table>
<?php echo form_close();?>
