
<h2>Create a news item</h2>

<?php echo validation_errors(); ?>

<?php echo form_open('regions/create') ?>

	<label for="name">Name</label>
	<input type="input" name="name" /><br />

	<label for="info">Info</label>
	<textarea name="info"></textarea><br />
	<label for="image">image</label>
	<textarea name="image"></textarea><br />
	<label for="lat_start">lat_start</label>
	<textarea name="lat_start"></textarea><br />
	<label for="lon_start">lon_start</label>
	<textarea name="lon_start"></textarea><br />
	<label for="lat_end">lat_end</label>
	<textarea name="lat_end"></textarea><br />
	<label for="lon_end">lon_end</label>
	<textarea name="lon_end"></textarea><br />


	<input type="submit" name="submit" value="Create region" />

</form>
