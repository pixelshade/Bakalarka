<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAm7a4WerB5sAzBPDPV2bDybFZYFKFJDi4&sensor=false">
</script>
<script type="text/javascript">
	var latlngs = [
	<?php
	foreach ($user_positions as $user_position) {
		echo   "new google.maps.LatLng($user_position->latitude, $user_position->longtitude),\n";
	}
	?>
	];

	var times = [
	<?php
	foreach ($user_positions as $user_position) {
		echo   "'".$user_position->time."',\n";
	}
	?>
	];

	var markers = [];
	var iterator = times.length -1;

	var map;

	function initialize() {
		<?php
		echo "var centerLatitude = ".$user_positions[0]->latitude.";\n";
		echo "var centerLongtitude = ".$user_positions[0]->longtitude.";\n";
		?>
		var mapOptions = {
			center: new google.maps.LatLng(centerLatitude, centerLongtitude),
			zoom: 16
		};
		map = new google.maps.Map(document.getElementById("googleMap"),	mapOptions);

		var flightPath = new google.maps.Polyline({
			path: latlngs,
			geodesic: true,
			strokeColor: '#FF0000',
			strokeOpacity: 1.0,
			strokeWeight: 2
		});

		flightPath.setMap(map);	
		drop();
	}


	function drop() {
		for (var i = 0; i < latlngs.length; i++) {
			setTimeout(function() {
				addMarker();
			}, i * 200);
		}
	}



	function addMarker() {
		markers.push(new google.maps.Marker({
			title: times[iterator],
			position: latlngs[iterator],
			map: map,
			draggable: false,
			animation: google.maps.Animation.DROP
		}));
		iterator--;
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div id="googleMap" style="height:380px;"></div>



