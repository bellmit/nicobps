

var app = angular.module("uploadEdcr", []);
app.controller('edcrscrutinyController', function($scope) {
	$scope.edcrscrutiny = {};
	$scope.submitDetails = function() {
		submitEdcrScrutiny($scope.edcrscrutiny);
	};

});



function submitEdcrScrutiny(edcrscrutiny) {
	var flag = confirm(" Click OK to Upload. Click Cancel if you want to review your entries");
	if (!flag) { return false; }
	jQuery.ajax({
		url: "scrutinize_edcr.htm",
		data: edcrscrutiny,
		contentType: "application/json; charset=utf-8",
		headers: {  "Access-Control-Allow-Origin:": "*"},
		type: "POST",
		success: function(data) {
			console.log(data);

		},
		error: function(request, status, error) {
	 
			alert(status + " : " + JSON.stringify(request));
			jQuery('#successMsg').html("* " + request.responseText).show();
		}
	});
}




