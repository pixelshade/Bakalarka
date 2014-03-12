<section>
	<h2>Users and their attributes</h2>
	<?php echo anchor('admin/user_attribute/edit', '<i class="icon-plus"></i> Add an user_attribute'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>char_id</th>
				<th>attribute_id</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_attributes)): foreach($user_attributes as $user_attribute): ?>	
		<tr>
			<td><?php echo anchor('admin/user_attribute/edit/' . $user_attribute->id, $user_attribute->char_id); ?></td>
			<td><?php echo $user_attribute->attribute_id; ?></td>
			
			<td><?php echo btn_edit('admin/user_attribute/edit/' . $user_attribute->id); ?></td>
			<td><?php echo btn_delete('admin/user_attribute/delete/' . $user_attribute->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="6">We could not find any user_attributes.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>