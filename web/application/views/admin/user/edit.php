 <?php 
  $default_rights_level = empty($user->id) ? $default_rights_level : $user->rights_level;
  ?>
 <div class="modal-header">
 	<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
 	<h4 class="modal-title"><?php echo empty($user->id) ? 'Add a new user' : 'Edit user'; ?></h4> 	
 </div>
 <div class="modal-body">
 	<?php echo validation_errors(); ?>
 	<?php echo form_open(); ?> 	
 	<table>
 		<tr>
 			<td>Name</td>
 			<td><?php echo form_input('name',set_value('name', $user->name), 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td>Email</td>
 			<td><?php echo form_input('email',set_value('email', $user->email), 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td>Password</td>
 			<td><?php echo form_password('password','', 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td>Confirm password</td>
 			<td><?php echo form_password('confirm_password','', 'class="form-control"') ?></td>
 		</tr>
 		<tr>
 			<td>User level</td>
 			<td><?php echo form_dropdown('submit', $rights_levels, $default_rights_level, 'class="form-control"'); ?></td>
 		</tr>
 		<tr>
 			<td></td>
 			<td><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
 		</tr>

 	</table>

 	<?php echo form_close(); ?>
 	
 </div>

