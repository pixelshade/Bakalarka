<h3><?php echo empty($quest->id) ? 'Add a new quest' : 'Edit quest ' . $quest->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>code</td>
		<td><?php echo form_input('code', set_value('code', $quest->code), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>region_id</td>		
		<td><?php echo form_dropdown('region_id',$regions, $quest->region_id, 'class="form-control"'); ?>	</td>
	</tr>
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
		<td><?php echo form_dropdown('image',$images, $quest->image, 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>autostart</td>		
		<td><?php 
		echo form_boolean_select('autostart', $quest->autostart); ?>	</td>
	</tr>
	<tr>
		<td>required_completed quest</td>
		<td><?php echo form_dropdown('required_completed_quest_id', $quests, $quest->required_completed_quest_id, 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>duration(seconds)</td>
		<td><?php echo form_input('duration',set_value('duration', $quest->duration), 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>image</td>
		<td><?php echo form_dropdown('image',$images, $quest->image, 'class="form-control"'); ?>	</td>
	</tr>
	
	<tr>
		<td>completion_requirement_type</td>
		<td><?php echo form_dropdown('completion_requirement_type',$completion_types, $quest->completion_requirement_type, 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>completion_requirement</td>
		<td><?php echo form_input('completion_requirement', set_value('completion_requirement', $quest->completion_requirement), 'class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>reward</td>
		<td><?php echo form_dropdown('reward_id',$rewards, $quest->reward_id, 'class="form-control"'); ?>	</td>
	</tr>
</table>
<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
<?php echo form_close();?>
