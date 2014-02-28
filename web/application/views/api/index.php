<section>
	<h2>Client api functions</h2>	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>name</th>				
			</tr>
		</thead>
		<tbody>
<?php 

foreach($functions as $function): ?>	
		<tr>
			<td><?php echo anchor('api/' . $function, $function); ?></td>			
		</tr>
<?php endforeach; ?>
		</tbody>
	</table>
</section>