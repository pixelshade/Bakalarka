<section>
	<h2>News regions</h2>
	<?php echo anchor('admin/region/edit', '<i class="icon-plus"></i> Add an region'); ?>
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
<?php if(count($regions)): foreach($regions as $region): ?>	
		<tr>
			<td><?php echo anchor('admin/region/edit/' . $region->id, $region->name); ?></td>
			<td><?php echo $region->info; ?></td>
			<td><?php echo $region->image; ?></td>
			<td><?php echo $region->lat_start; ?></td>
			<td><?php echo $region->lon_start; ?></td>
			<td><?php echo $region->lat_end; ?></td>
			<td><?php echo $region->lon_end; ?></td>			
			<td><?php echo btn_edit('admin/region/edit/' . $region->id); ?></td>
			<td><?php echo btn_delete('admin/region/delete/' . $region->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="3">We could not find any regions.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>