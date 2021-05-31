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
<<<<<<< Updated upstream

		listOffices : function(callback) {
			$http.get("./listOffices.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},		
		listEnclosures : function(callback) {
			$http.get("./listEnclosures.htm").success(
=======
		listSubOccupancy: function(callback) {
			$http.get("./listSubOccupancy.htm").success(
>>>>>>> Stashed changes
					function(response, status, headers, config) {
						callback(response);
					});
		},
<<<<<<< Updated upstream
		listOffices : function(callback) {
			$http.get("./listOffices.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},		
		listEnclosures : function(callback) {
			$http.get("./listEnclosures.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},
			

=======
			listUsages: function(callback) {
			$http.get("./listUsages.htm").success(
					function(response, status, headers, config) {
						callback(response);
					});
		},	
		
		
>>>>>>> Stashed changes
	}
});