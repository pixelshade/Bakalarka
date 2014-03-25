<h3><?php echo empty($quest->id) ? 'Add a new quest' : 'Edit quest ' . $quest->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
</head>


<table class="table">
	<tr>
		<td>region_id</td>		
		<td><?php echo form_dropdown('region_id',$regionNames, $quest->region_id, 'class="form-control"'); ?>	</td>
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
		<td><?php echo form_dropdown('image',$imageNames, $quest->image, 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>autostart</td>		
		<td><?php 
			echo form_boolean_select('autostart', $quest->autostart); ?>	</td>
		</tr>
		<tr>
			<td>required_completed quest</td>
			<td><?php echo form_dropdown('required_completed_quest_id', $questNames, $quest->required_completed_quest_id, 'class="form-control"'); ?>	</td>
		</tr>
		<tr>
			<td>duration(seconds)</td>
			<td><?php echo form_input('duration',set_value('duration', $quest->duration), 'class="form-control"'); ?>	</td>
		</tr>	
		<tr>
			<td>completion_requirement_type</td>
			<td><?php echo form_dropdown('completion_requirement_type',$completion_types, $quest->completion_requirement_type, 'id="requirement_type" class="form-control"'); ?>	</td>
		</tr>
		<tr>
			<td>completion_requirement</td>
			<td id="requirement">			
				<?php echo form_input('completion_requirement', set_value('completion_requirement', $quest->completion_requirement), 'id="0" class="form-control"'); ?>
				<?php echo form_dropdown('completion_requirement',$itemNames, $quest->completion_requirement, 'id="1" class="form-control"'); ?>
				<?php echo form_dropdown('completion_requirement',$questNames, $quest->completion_requirement, 'id="2" class="form-control"'); ?>
				<?php echo form_dropdown('completion_requirement',$attributeNames, $quest->completion_requirement, 'id="3" class="form-control"'); ?>
				<?php echo form_dropdown('completion_requirement',$regionNames, $quest->completion_requirement, 'id="4" class="form-control"'); ?>
			</td>
		</tr>
		<tr>
			<td>reward</td>
			<td><?php echo form_dropdown('reward_id',$rewardNames, $quest->reward_id, 'class="form-control"'); ?>	</td>
		</tr>
	</table>
	<?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?>
	<?php echo form_close();?>

	<script type="text/javascript">
		function update(){
			var selected = $('#requirement_type').val();
			$('#requirement > .form-control').attr('disabled',true);	
			$('#requirement > .form-control').hide();
			$('#'+selected).attr('disabled',false);
			$('#'+selected).show();			
		}

		$(update());
		$('#requirement_type').on('change',update);

	</script>



