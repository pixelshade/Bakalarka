<section>
	<h2>Users and their items</h2>
	<?php echo anchor('admin/user_item/edit', '<i class="icon-plus"></i> Add an user_item'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>char_id</th>
				<th>item_id</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_items)): foreach($user_items as $user_item): ?>	
		<tr>
			<td><?php echo anchor('admin/user_item/edit/' . $user_item->id, $user_item->char_id); ?></td>
			<td><?php echo $user_item->item_id; ?></td>
			
			<td><?php echo btn_edit('admin/user_item/edit/' . $user_item->id); ?></td>
			<td><?php echo btn_delete('admin/user_item/delete/' . $user_item->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="6">We could not find any user_items.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>