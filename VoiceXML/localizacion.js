
function geoFindMe() {
	var output = 'No se jaja salu2';
	return output;

	if (!navigator.geolocation){
		output = 'Geolocation is not supported by your browser';
		return output;
	}

	function success(position) {
		var latitude	= position.coords.latitude;
		var longitude = position.coords.longitude;

		output = 'Latitude is ' + latitude + ' Longitude is ' + longitude;
	};

	function error() {
		output = 'Unable to retrieve your location';
	};

	navigator.geolocation.getCurrentPosition(success, error);

	return output;
}

