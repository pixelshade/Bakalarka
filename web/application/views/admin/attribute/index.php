<section>
	<h2>Attributes</h2>
	<?php echo anchor('admin/attribute/edit', '<i class="icon-plus"></i> Add an attribute'); ?>
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
<?php if(count($attributes)): foreach($attributes as $attribute): ?>	
		<tr>
			<td><?php echo anchor('admin/attribute/edit/' . $attribute->id, $attribute->name); ?></td>
			<td><?php echo $attribute->info; ?></td>
			<td><?php echo $attribute->image; ?></td>					
			<td><?php echo btn_edit('admin/attribute/edit/' . $attribute->id); ?></td>
			<td><?php echo btn_delete('admin/attribute/delete/' . $attribute->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="5">We could not find any attributes.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>