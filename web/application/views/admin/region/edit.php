<h3><?php echo empty($region->id) ? 'Add a new region' : 'Edit region ' . $region->name; ?></h3>
<?php echo validation_errors(); ?>
<?php echo form_open(); ?>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAm7a4WerB5sAzBPDPV2bDybFZYFKFJDi4&sensor=true">
</script>

<script>

// This example adds a user-editable rectangle to the map.
// When the user changes the bounds of the rectangle,
// an info window pops up displaying the new bounds.

var rectangle;
var map;
var infoWindow;

function initialize() {
	<?php
	if(empty($region->id))	{
		$lat = 48.15393334543018;
		$lon = 17.103288173675537;
	
	    echo "var bounds = new google.maps.LatLngBounds(";		
		echo "new google.maps.LatLng(".$lat.",".$lon."),";
		echo "new google.maps.LatLng(".($lat+0.002).",".($lon+0.002).")";
		echo ");";

	} else {
		$lat = ($region->lat_start + $region->lat_end) / 2;
		$lon = ($region->lon_start +$region->lon_end) / 2;

		echo "var bounds = new google.maps.LatLngBounds(";		
		echo "new google.maps.LatLng(".$region->lat_start.",".$region->lon_start."),";
		echo "new google.maps.LatLng(".$region->lat_end.",".$region->lon_end.")";
		echo ");";
	}



	echo "var centerMap = new google.maps.LatLng(".$lat.",".$lon.");";
	?>
	var mapOptions = {
		center: centerMap,
		zoom: 15
	};
	map = new google.maps.Map(document.getElementById('googleMap'),	mapOptions);

	

		

  // Define the rectangle and set its editable property to true.
  rectangle = new google.maps.Rectangle({
  	bounds: bounds,
  	editable: true,
  	draggable: true
  });

  rectangle.setMap(map);

  // Add an event listener on the rectangle.
  google.maps.event.addListener(rectangle, 'bounds_changed', showNewRect);

  // Define an info window on the map.
  infoWindow = new google.maps.InfoWindow();
}
// Show the new coordinates for the rectangle in an info window.

/** @this {google.maps.Rectangle} */
function showNewRect(event) {
	var ne = rectangle.getBounds().getNorthEast();
	var sw = rectangle.getBounds().getSouthWest();

	var contentString = '<b>Region moved.</b><br>' +
	'New north-east corner: ' + ne.lat() + ', ' + ne.lng() + '<br>' +
	'New south-west corner: ' + sw.lat() + ', ' + sw.lng();

	document.getElementById('lat_end').value = ne.lat();
	document.getElementById('lon_end').value = ne.lng();
	document.getElementById('lat_start').value = sw.lat();
	document.getElementById('lon_start').value = sw.lng();



  // Set the info window's content and position.


  infoWindow.setContent(contentString);
  infoWindow.setPosition(ne);

  infoWindow.open(map);
}
google.maps.event.addDomListener(window, 'load', initialize);

//  ADD movement







</script>	

<table class="table">
	<tr>
		<td>name</td>
		<td><?php echo form_input('name', set_value('name', $region->name), 'class="form-control"'); ?></td>		
	</tr>
	<tr>
		<td>info</td>
		<td><?php echo form_input('info', set_value('info', $region->info), 'class="tinymce"'); ?></td>
	</tr>
	<tr>
		<td>image</td>
		<td><?php echo form_dropdown('image',$images,  $region->image, 'class="form-control"'); ?>	</td>
	</tr>
	<tr>
		<td>map</td>
		<td><div id="googleMap" style="height:380px;"></div></td>
	</tr>
</table>
<table class="table">
	<tr>		
		<td colspan="2"><?php echo "<button type='button' class='btn btn-primary' id='add_movement_btn'>+ add movement</button>" ?></td>
		<td colspan="2"><?php echo "<button type='button' class='btn btn-primary' id='play_movement_btn'>play movement</button>" ?></td>
	</tr>	
	<tr>		
		<td colspan="4"><?php echo form_input('movement', set_value('movement', $region->movement), 'id="movement_json" class="form-control"'); ?></td>
	</tr>
	<tr>
		<td>lat_start</td>
		<td><?php echo form_input('lat_start', set_value('lat_start', $region->lat_start), 'class="form-control" id="lat_start"'); ?></td>
		<td>lon_start</td>
		<td><?php echo form_input('lon_start', set_value('lon_start', $region->lon_start), 'class="form-control" id="lon_start"'); ?></td>
	</tr>
	<tr>
		<td>lat_end</td>
		<td><?php echo form_input('lat_end', set_value('lat_end', $region->lat_end), 'class="form-control" id="lat_end"'); ?></td>
		<td>lon_end</td>
		<td><?php echo form_input('lon_end', set_value('lon_end', $region->lon_end), 'class="form-control" id="lon_end"'); ?></td>
	</tr>
	<tr>		
		<td colspan="4"><?php echo form_submit('submit', 'Save', 'class="btn btn-primary"'); ?></td>
	</tr>
</table>
<?php echo form_close();?>





<script>

function log(x){ console.log(x) }
for(var i = 0; i < 10; i++){ setTimeout(log(i), 500*i) }


	function animate(){
		var prev = null;
		var jsonArr = JSON.parse($('#movement_json').val());		
		if(jsonArr!=null){
			for (var i = 0; i <= jsonArr.length; i++) {
				setTimeout(function(a){ 
					if(prev!=null) prev.setMap(null);
					prev = draw_rect(jsonArr[a]);
				}, i*1000,i);
			}
		}

	}

	function draw_rect(position){

		if(position!=null || position != undefined){					
			console.log(position);

			var rec = new google.maps.Rectangle({
				strokeColor: '#FF0000',
				strokeOpacity: 0.8,
				strokeWeight: 2,
				fillColor: '#FF0000',
				fillOpacity: 0.35,
				map: map,
				bounds: new google.maps.LatLngBounds(
					new google.maps.LatLng(position.lat_start, position.lon_start),
					new google.maps.LatLng(position.lat_end, position.lon_end))
			});
			return rec;

		} 

	}

	$('#play_movement_btn').click(function(){
		animate();


	})



	$('#add_movement_btn').click(function(){
		add_movement();
		alert('Position added');
	}	
	)


	function add_movement(){
		var lats = $('#lat_start').val();
		var lons = $('#lon_start').val();
		var late = $('#lat_end').val();
		var lone = $('#lon_end').val();
		var jsonArr = $('#movement_json').val();

		jsonArr = add_fields_to_json( lats, lons, late, lone, jsonArr);
		$('#movement_json').val(JSON.stringify(jsonArr));	
	}


	function add_fields_to_json(lat_start0, lon_start0, lat_end0, lon_end0, json){
		var position = {
			lat_start : lat_start0,
			lon_start : lon_start0,
			lat_end : lat_end0,
			lon_end : lon_end0
		}
		if(json==""){
			json = [];
		} else {
			json = JSON.parse(json);
		}
		json.push(position);	
		return json;
	}
</script>