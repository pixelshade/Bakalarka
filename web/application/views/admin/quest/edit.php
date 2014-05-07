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
				<?php echo form_input('completion_requirement', set_value('completion_requirement', $quest->completion_requirement), ' class="form-control type0"'); ?>
				<?php echo '<input type="hidden" id="mixed_source" name="completion_requirement" value="'.$quest->completion_requirement.'" class="form-control type1">'; ?>
				<?php echo form_dropdown('item_id',$itemNames, '', 'id="item_id" class="form-control type1"'); ?>
				<?php echo form_input('item_amount', '', 'id="item_amount"  placeholder="minimum amount needed" class="form-control type1"'); ?>				
				<?php echo form_dropdown('completion_requirement',$questNames, $quest->completion_requirement, ' class="form-control type2"'); ?>
				<?php echo form_dropdown('completion_requirement',$attributeNames, $quest->completion_requirement, ' class="form-control type3"'); ?>
				<?php echo form_dropdown('completion_requirement',$regionNames, $quest->completion_requirement, ' class="form-control type4"'); ?>
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
			$('.type'+selected).attr('disabled',false);
			$('.type'+selected).show();			
			$('.type1').on('change',function(){join_and_set_value('mixed_source','item_id', 'item_amount')});		
			$(split_and_set_value('mixed_source','item_id', 'item_amount'));
		}

		$(update());
		$('#requirement_type').on('change',update);		


		function split_and_set_value(mixed_source,source_id, amount_id){
			var mixedStr = $('#'+mixed_source).val();
			var arr = mixedStr.split("|");			
			if(arr.length > 0){
				var source = arr[0];
				var amount = arr[1];
				$('#'+source_id).val(source);
				$('#'+amount_id).val(amount);
			}
		}

		function join_and_set_value(mixed_source,source_id, amount_id){
			var source = $('#'+source_id).val();
			var amount = $('#'+amount_id).val();
			var mixedStr = source + "|" + amount;
			$('#'+mixed_source).val(mixedStr);	
			console.log(mixedStr)		;
		}

	</script>



