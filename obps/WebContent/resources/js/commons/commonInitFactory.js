//Common Factories
app.factory('commonInitFactory', function($http) {
	return {	
		listUsers : function(callback) {
			$http.get("./listOfficeUsers.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},		
		listLicensees : function(callback) {
			$http.get("./listLicensees.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listFeeTypes: function(callback) {
			$http.get("./listFeeTypes.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listFileTypes: function(callback) {
			$http.get("./listFileTypes.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listQuestionaires: function(callback) {
			$http.get("./listQuestionaires.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listOccupancies: function(callback) {
			$http.get("./listOccupancies.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listFeeMaster: function(callback) {
			$http.get("./listFeeMaster.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listOffices : function(callback) {
			$http.get("./listOffices.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},		
		listEnclosures : function(callback) {
			$http.get("./listEnclosures.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},
		
		listSubOccupancy: function(callback) {
			$http.get("./listSubOccupancy.htm").then(

					function(response, status, headers, config) {
						callback(response.data);
					});
		},
				
			listUsages: function(callback) {
			$http.get("./listUsages.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		listFiletypes: function(callback) {
			$http.get("./listFiletypes.htm").then(
					function(response, status, headers, config) {
						callback(response.data);
					});
		},	
		
		
	}
});