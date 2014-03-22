<section>
	<h2>Rewards</h2>
	<?php echo anchor('admin/reward/edit', '<i class="icon-plus"></i> Add an reward'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>
				<th>code</th>
				<th>item_definition_id</th>
				<th>item_amount</th>
				<th>attribute_id</th>
				<th>attribute_amount</th>				
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			<?php if(count($rewards)): foreach($rewards as $reward): ?>	
				<tr>
					<td><?php echo anchor('admin/reward/edit/' . $reward->id, $reward->name); ?></td>
					<td><?php if(!empty($reward->code)) echo '<a href="/admin/qrcode_generator/get/'.$reward->code.'">'.$reward->code.'</a>';?> </td>
					<td><?php echo $reward->item_definition_id; ?></td>
					<td><?php echo $reward->item_amount; ?></td>
					<td><?php echo $reward->attribute_id; ?></td>
					<td><?php echo $reward->attribute_amount; ?></td>

					<td><?php echo btn_edit('admin/reward/edit/' . $reward->id); ?></td>
					<td><?php echo btn_delete('admin/reward/delete/' . $reward->id); ?></td>
				</tr>
			<?php endforeach; ?>
		<?php else: ?>
			<tr>
				<td colspan="7">We could not find any rewards.</td>
			</tr>
		<?php endif; ?>	
	</tbody>
</table>
</section>