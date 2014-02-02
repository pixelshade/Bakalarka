<?php

echo '<h2>'.$region['name'].'</h2>';
echo '<table class="table table-striped">';
foreach ($region as $key => $value) {
	echo "<tr><td>$key</td><td>$value</td></tr>";
}
echo "</table>";

?>
 <p><a href="./">View regions</a></p>