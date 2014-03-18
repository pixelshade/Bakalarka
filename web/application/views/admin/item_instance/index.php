<section>
	<h2>Item instances</h2>
	<?php echo anchor('admin/item_instance/edit', '<i class="icon-plus"></i> Add an item instance'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>item_definition_id</th>
				<th>latitude</th>
				<th>longtitude</th>	
				<th>amount</th>
				<th>added_by_user</th>
				<th>code</th>	
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($item_instances)): foreach($item_instances as $item_instance): ?>	
		<tr>
			<td><?php echo anchor('admin/item_instance/edit/' . $item_instance->id, $item_names[$item_instance->item_definition_id]); ?></td>
			<td><?php echo $item_instance->latitude; ?></td>
			<td><?php echo $item_instance->longtitude; ?></td>
			<td><?php echo $item_instance->amount; ?></td>
			<td><?php echo $item_instance->added_by_user; ?></td>
			<td><?php echo anchor('admin/qrcode_generator/get/'.$item_instance->code,$item_instance->code); ?></td>					
			<td><?php echo btn_edit('admin/item_instance/edit/' . $item_instance->id); ?></td>
			<td><?php echo btn_delete('admin/item_instance/delete/' . $item_instance->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="5">We could not find any item_instances.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>