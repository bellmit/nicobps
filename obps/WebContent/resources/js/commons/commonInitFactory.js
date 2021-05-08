//Common Factories
app.factory('commonInitFactory', function($http) {
	return {		
		listUsers : function(callback) {
			$http.get("./listUsers.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},		
			
	}
});