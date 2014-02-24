<section>
	<h2>News quests</h2>
	<?php echo anchor('admin/quest/edit', '<i class="icon-plus"></i> Add an quest'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>
				<th>info</th>
				<th>image</th>
				<th>lat_start</th>
				<th>lon_start</th>
				<th>lat_end</th>
				<th>lon_end</th>
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($quests)): foreach($quests as $quest): ?>	
		<tr>
			<td><?php echo anchor('admin/quest/edit/' . $quest->id, $quest->name); ?></td>
			<td><?php echo $quest->info; ?></td>
			<td><?php echo $quest->image; ?></td>
			<td><?php echo $quest->lat_start; ?></td>
			<td><?php echo $quest->lon_start; ?></td>
			<td><?php echo $quest->lat_end; ?></td>
			<td><?php echo $quest->lon_end; ?></td>			
			<td><?php echo btn_edit('admin/quest/edit/' . $quest->id); ?></td>
			<td><?php echo btn_delete('admin/quest/delete/' . $quest->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="3">We could not find any quests.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>