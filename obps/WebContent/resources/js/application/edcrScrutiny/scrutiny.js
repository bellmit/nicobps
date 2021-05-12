
jQuery(document).ready(function() {

});

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
	var data = new FormData();
	jQuery.each(jQuery('#dxffile')[0].files, function(i, file) {
		data.append('planFile', file);
	});

	jQuery.ajax({
		type: "POST",
		url: "./scrutinize_edcr1.htm",
		cache: false,
		contentType: false,
		dataType: 'json',
		processData: false,
		data: data,
		success: function(response) {
			alert(JSON.stringify(response));
			
			
		},
		error: function(xhr) {
			alert(xhr.status + " = " + xhr);
			alert(
				"Sorry, there was an error while trying to process the request."
			);
		},
	});

}




