<section>
	<h2>Users and their qrscanneds</h2>
	<?php echo anchor('admin/user_qrscanned/edit', '<i class="icon-plus"></i> Add an user_qrscanned'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>char_id</th>
				<th>qrscanned</th>
				
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($user_qrscanneds)): foreach($user_qrscanneds as $user_qrscanned): ?>	
		<tr>
			<td><?php echo $user_qrscanned->char_id; ?></td>
			<td><?php echo $user_qrscanned->qrscanned; ?></td>			
			
			<td><?php echo btn_edit('admin/user_qrscanned/edit/' . $user_qrscanned->id); ?></td>
			<td><?php echo btn_delete('admin/user_qrscanned/delete/' . $user_qrscanned->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="6">We could not find any user_qrscanneds.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>