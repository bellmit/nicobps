<!DOCTYPE html>
<html>
	<head>
		<title>Geocoding Service</title>
<!-- 		    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCH9PmCbk_mcpgijAAlTeltC4deOxC5wEM&v=3.exp&libraries=geometry,drawing,places" -->
<!--     		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4u5cL3l3jl7dpRnoIcl8drvgpK5uB_bc&callback=initMap&libraries=&v=weekly" -->
		<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
		<!-- jsFiddle will insert css and js -->	
		<style>
			/* Always set the map height explicitly to define the size of the div
			       * element that contains the map. */
			#map {
				height: 100%;
			}
			
			/* Optional: Makes the sample page fill the window. */
			html, body {
				height: 100%;
				margin: 0;
				padding: 0;
			}
			
			#floating-panel {
				position: absolute;
				top: 10px;
				left: 25%;
				z-index: 5;
				background-color: #fff;
				padding: 5px;
				border: 1px solid #999;
				text-align: center;
				font-family: "Roboto", "sans-serif";
				line-height: 30px;
				padding-left: 10px;
			}
		</style>
		<!-- <script>
			function initMap() {
				let position = {lat: -34.397, lng: 150.644 };
				navigator.geolocation.getCurrentPosition((curpos) => {
					console.log("geolocation");
					position.lat = curpos.coords.latitude;
					position.lng = curpos.coords.longitude;
				});
				console.log("position: ",position);
				  const map = new google.maps.Map(document.getElementById("map"), {
				    zoom: 8,
				    center: position,
				  });
				  const geocoder = new google.maps.Geocoder();
				  document.getElementById("submit").addEventListener("click", () => {
				    geocodeAddress(geocoder, map);
				  });

				  var scope = angular.element($("#appId")).scope();
				  document.getElementById("pickCityBtn").addEventListener("click", () => {
					  console.log("marker.position: ",marker.position);
				    scope.$apply(function () {
					    scope.setGoogleMapLocation({lat: marker.position.lat(), lng: marker.position.lng()});
	                });
				  });
				  var marker =  new google.maps.Marker({
				      position: position,
				      map,
				    }); 
				  map.addListener("click", (mapsMouseEvent) => {
					  marker.setMap(null);
					    marker = new google.maps.Marker({
					      position: mapsMouseEvent.latLng,
					      map,
					      title: "Hello World!",
					    });
					    map.setCenter(mapsMouseEvent.latLng);
					    
					    scope.$apply(function () {
						    scope.setGoogleMapLocation(mapsMouseEvent.latLng.toJSON());
		                });
				  });
				}
	
				function geocodeAddress(geocoder, resultsMap) {
				  const address = document.getElementById("address").value;
				  geocoder.geocode({ address: address }, (results, status) => {
				    if (status === "OK") {
				      resultsMap.setCenter(results[0].geometry.location);
				      new google.maps.Marker({
				        map: resultsMap,
				        position: results[0].geometry.location,
				      });
				    } else {
				      alert("Geocode was not successful for the following reason: " + status);
				    }
				  });
				}
		</script> -->
		<script type="text/javascript" src="resources/js/application/bpa/googlemapapi.js"></script>
	</head>
	<body>
		<div id="floating-panel">
			<div class="input-group">
				<input id="gmapaddress" class="form-control" type="textbox" value="Sydney, NSW" ng-model="gmapAddress"/>
				<div class="input-group-append">
					<input id="submit" type="button" value="Geocode" />
				</div> 
			</div>
		</div>
		<div id="map"></div>
	
		<!-- Async script executes immediately and must be after any DOM elements used in callback. -->
		<script
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCH9PmCbk_mcpgijAAlTeltC4deOxC5wEM&callback=initMap&libraries=&v=weekly"
			async></script>
	</body>
</html>