<section>
	<h2>Users and their objectives</h2>
	<?php echo anchor('admin/user_objective/edit', '<i class="icon-plus"></i> Add an user_objective'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>char_id</th>
				<th>objective_id</th>
				<th>completed</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_objectives)): foreach($user_objectives as $user_objective): ?>	
		<tr>
			<td><?php echo anchor('admin/user_objective/edit/' . $user_objective->id, $user_objective->char_id); ?></td>
			<td><?php echo $user_objective->objective_id; ?></td>
			<td><?php echo $user_objective->completed; ?></td>
			
			<td><?php echo btn_edit('admin/user_objective/edit/' . $user_objective->id); ?></td>
			<td><?php echo btn_delete('admin/user_objective/delete/' . $user_objective->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="5">We could not find any user_objectives.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>