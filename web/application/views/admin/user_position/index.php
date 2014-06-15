<section>
	<h2>Users and their positions</h2>
	<a href="/admin/user_position/delete_all" class="btn btn-danger">Delete all</a>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>User</th>
				<th>latitude</th>
				<th>longtitude</th>
				<th>time</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_positions)): foreach($user_positions as $user_position): ?>	
		<tr>

			<td><?php echo anchor('admin/user_position/show/'.$user_position->char_id, '<i class="icon-zoom"></i>'.$chars[$user_position->char_id]); ?></td>
			<td><?php echo $user_position->latitude; ?></td>
			<td><?php echo $user_position->longtitude; ?></td>
			<td><?php echo $user_position->time; ?></td>
			
			<td><?php echo btn_edit('admin/user_position/edit/' . $user_position->id); ?></td>
			<td><?php echo btn_delete('admin/user_position/delete/' . $user_position->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="6">We could not find any user_positions.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>