 <div class="modal-header">
 	<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
 	<h4 class="modal-title">Log in</h4> 	
 </div>
 <div class="modal-body">
 	<?php echo validation_errors(); ?>
 	<?php echo form_open(); ?> 	
 	<table>
 		<tr>
 			<td>Email</td>
 			<td><?php echo form_input('email','', 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td>Password</td>
 			<td><?php echo form_password('password','', 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td></td>
 			<td><?php echo form_submit('submit', 'Log in', 'class="btn btn-primary"'); ?></td>
 		</tr>

 	</table>

 	<?php echo form_close(); ?>
 	
 </div>

