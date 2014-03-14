<section>
	<h2>Users and their quests</h2>
	<?php echo anchor('admin/user_quest/edit', '<i class="icon-plus"></i> Add an user_quest'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>char_id</th>
				<th>quest_id</th>
				<th>time_accepted</th>
				<th>completed</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_quests)): foreach($user_quests as $user_quest): ?>	
		<tr>
			<td><?php echo $user_quest->char_id; ?></td>
			<td><?php echo $user_quest->quest_id; ?></td>
			<td><?php echo $user_quest->time_accepted; ?></td>
			<td><?php echo $user_quest->completed; ?></td>
			
			<td><?php echo btn_edit('admin/user_quest/edit/' . $user_quest->id); ?></td>
			<td><?php echo btn_delete('admin/user_quest/delete/' . $user_quest->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="6">We could not find any user_quests.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>