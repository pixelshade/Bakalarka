<section>
	<h2>Item definitions</h2>
	<?php echo anchor('admin/item_definition/edit', '<i class="icon-plus"></i> Add an item definition'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>
				<th>info</th>
				<th>image</th>		
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($item_definitions)): foreach($item_definitions as $item_definition): ?>	
		<tr>
			<td><?php echo anchor('admin/item_definition/edit/' . $item_definition->id, $item_definition->name); ?></td>
			<td><?php echo $item_definition->info; ?></td>
			<td><?php echo $item_definition->image; ?></td>					
			<td><?php echo btn_edit('admin/item_definition/edit/' . $item_definition->id); ?></td>
			<td><?php echo btn_delete('admin/item_definition/delete/' . $item_definition->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="5">We could not find any item_definitions.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>