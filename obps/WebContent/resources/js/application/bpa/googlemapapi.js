/**
 * @author Decent Khongstia
 */

var position = { lat: -34.397, lng: 150.644 };
navigator.geolocation.getCurrentPosition((curpos) => {
	position.lat = curpos.coords.latitude;
	position.lng = curpos.coords.longitude;
});

function initMap() {
	const map = new google.maps.Map(document.getElementById("map"), {
		zoom: 8,
		center: position,
	});
	const geocoder = new google.maps.Geocoder();
	var marker = new google.maps.Marker({
		position: position,
		map,
	});
	document.getElementById("submit").addEventListener("click", () => {
		geocodeAddress(geocoder, map);
	});

	var scope = angular.element($("#appId")).scope();
	document.getElementById("pickCityBtn").addEventListener("click", () => {
		scope.$apply(function () {
			scope.setGoogleMapLocation({ lat: marker.position.lat(), lng: marker.position.lng() });
		});
	});

	map.addListener("click", (event) => {
		marker.setMap(null);
		marker = new google.maps.Marker({
			position: event.latLng,
			map,
		});
		map.setCenter(event.latLng);

		scope.$apply(function () {
			scope.setGoogleMapLocation(event.latLng.toJSON());
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
