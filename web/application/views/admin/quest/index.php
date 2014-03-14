<section>
	<h2>Quests</h2>
	<?php echo anchor('admin/quest/edit', '<i class="icon-plus"></i> Add an quest'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>
				<th>code</th>
				<th>info</th>
				<th>image</th>
				<th>reward_id</th>
				<th>autostart</th>
				<th>region_id</th>
				<th>required completed quest to start</th>
				<th>duration(seconds)</th>
				<th>quest completion type</th>
				<th>completion</th>

				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($quests)): foreach($quests as $quest): ?>	
		<tr>
			<td><?php echo anchor('admin/quest/edit/' . $quest->id, $quest->name); ?></td>
			<td><?php echo $quest->code; ?></td>
			<td><?php echo $quest->info; ?></td>
			<td><?php echo $imageNames[$quest->image]; ?></td>
			<td><?php echo $rewardNames[$quest->reward_id]; ?></td>
			<td><?php echo $quest->autostart; ?></td>
			<td><?php echo $regionNames[$quest->region_id]; ?></td>
			<td><?php echo $questNames[$quest->required_completed_quest_id]; ?></td>
			<td><?php echo $quest->duration; ?></td>	
			<td><?php echo $quest->completion_requirement_type; ?></td>
			<td><?php echo $quest->completion_requirement; ?></td>	

			<td><?php echo btn_edit('admin/quest/edit/' . $quest->id); ?></td>
			<td><?php echo btn_delete('admin/quest/delete/' . $quest->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="11">We could not find any quests.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>