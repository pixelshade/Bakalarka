<section>
	<h2>Cron tasks</h2>
	<?php echo anchor('admin/cron_task/edit', '<i class="icon-plus"></i> Add an cron_task'); ?>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>
				<th>task_type</th>
				<th>active</th>
				<th>params</th>
				
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
<?php if(count($cron_tasks)): foreach($cron_tasks as $cron_task): ?>	
		<tr>
			<td><?php echo anchor('admin/cron_task/edit/' . $cron_task->id, $cron_task->name); ?></td>
			<td><?php echo $cron_task->task_type; ?></td>
			<td><?php echo $cron_task->active; ?></td>
			<td><?php echo $cron_task->json_params; ?></td>
					
			<td><?php echo btn_edit('admin/cron_task/edit/' . $cron_task->id); ?></td>
			<td><?php echo btn_delete('admin/cron_task/delete/' . $cron_task->id); ?></td>
		</tr>
<?php endforeach; ?>
<?php else: ?>
		<tr>
			<td colspan="9">We could not find any cron_tasks.</td>
		</tr>
<?php endif; ?>	
		</tbody>
	</table>
</section>