//Common Factories
app.factory('commonInitFactory', function($http) {
	return {		
		listUsers : function(callback) {
			$http.get("./listUsers.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},		
		listLicensees : function(callback) {
			$http.get("./listLicensees.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},	
		listFeeTypes: function(callback) {
			$http.get("./listFeeTypes.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},	
		listOccupancies: function(callback) {
			$http.get("./listOccupancies.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},	
		listFeeMaster: function(callback) {
			$http.get("./listFeeMaster.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},	
	}
});