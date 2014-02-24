<h3><?php echo empty($quest->id) ? 'Add a new quest' : 'Edit quest ' . $quest->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>name</td>
		<td><?php echo form_input('name', set_value('name', $quest->name), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>info</td>
		<td><?php echo form_input('info', set_value('info', $quest->info), 'class="tinymce"'); ?></td>
	</tr>
	<tr>
		<td>image</td>
		<td><?php echo form_dropdown('image',$images, '', 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>map</td>
		<td><div id="googleMap" style="height:380px;"></div></td>
	</tr>
</table>
<table class="table">
	<tr>
		<td>lat_start</td>
		<td><?php echo form_input('lat_start', set_value('lat_start', $quest->lat_start), 'class="form-control" id="lat_start"'); ?></td>
		<td>lon_start</td>
		<td><?php echo form_input('lon_start', set_value('lon_start', $quest->lon_start), 'class="form-control" id="lon_start"'); ?></td>
	</tr>
	<tr>
		<td>lat_end</td>
		<td><?php echo form_input('lat_end', set_value('lat_end', $quest->lat_end), 'class="form-control" id="lat_end"'); ?></td>
		<td>lon_end</td>
		<td><?php echo form_input('lon_end', set_value('lon_end', $quest->lon_end), 'class="form-control" id="lon_end"'); ?></td>
	</tr>
	<tr>		
		<td colspan="4"><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
	</tr>
</table>
<?php echo form_close();?>
