var app = angular.module("CommonApp", []);
app.controller('edcrscrutinyController', function($scope, $timeout, $window) {

	$scope.generateReport = (planreport) => {
		//		alert('redirect');
		//		$window.location.href = planreport;
		$window.open(planreport, '_blank');
	}

	$scope.submitDetails = () => {
		var flag = confirm(" Click OK to Upload. Click Cancel if you want to review your entries");
		if (!flag) { return false; }
		var data = new FormData();
		jQuery.each(jQuery('#dxffile')[0].files, function(i, file) {
			data.append('planFile', file);
		});
		jQuery.ajax({
			type: "POST",
			url: "./scrutinize_edcr.htm",
			cache: false,
			contentType: false,
			dataType: 'json',
			processData: false,
			data: data,
			success: function(response) {

				$timeout(function() {
					$scope.edcrscrutiny = response;
				}, 0);
				if (response.status != '') {
					if (response.status == 'error') {
						jQuery('#msg').css("color", "red")

						jQuery('#msg').html('<div class="card text-white bg-warning mb-3" style="width: 100%;">' +
							'<div class="card-body"><h5 class="card-title">eDCR Scrutiny Failed: Error-Please contact System Administrator.</h5></div>' +
							'</div>');
					} else {
						jQuery('#entrytable').css("visibility", "hidden");
						jQuery('.edcrResp').css("visibility", "visible");
						jQuery('#genReport').css("visibility", "visible");
						if (response.status = "Accepted") {
							jQuery('#edcrstatus').css("color", "green")
						} else {
							jQuery('#edcrstatus').css("color", "red")
						}
						jQuery('#edcrnumber').css("color", "blue")
					}

				}
			},
			error: function(xhr) {
				alert(xhr.status + " = " + xhr);
				alert(
					"Sorry, there was an error while trying to process the request."
				);
			},
		});
	};

});


//app.controller("edcrscrutinyController", [
//	"$scope", "$http", "$timeout", "commonInitService",
//	function($scope, $http, $timeout, commonInitService) {
//		$scope.submitDetails = () => {
//			var flag = confirm(" Click OK to Upload. Click Cancel if you want to review your entries");
//			if (!flag) { return false; }
//			var data = new FormData();
//			jQuery.each(jQuery('#dxffile')[0].files, function(i, file) {
//				data.append('planFile', file);
//			});
//			jQuery.ajax({
//				type: "POST",
//				url: "./scrutinize_edcr.htm",
//				cache: false,
//				contentType: false,
//				dataType: 'json',
//				processData: false,
//				data: data,
//				success: function(response) {
//					$timeout(function() {
//						$scope.edcrscrutiny = response;
//					}, 0);
//					dataresponse = response;
//					if (response.status != '') {
//						jQuery('#entrytable').css("visibility", "hidden");
//						jQuery('#edcrResp').css("visibility", "visible");
//						if (response.status = "Accepted") {
//							jQuery('#edcrstatus').css("color", "green")
//						} else {
//							jQuery('#edcrstatus').css("color", "red")
//						}
//						jQuery('#edcrnumber').css("color", "blue")
//						jQuery('#edcrstatus').text(response.status);
//						jQuery('#edcrnumber').text(response.edcrnumber);
//					}
//				},
//				error: function(xhr) {
//					alert(xhr.status + " = " + xhr);
//					alert(
//						"Sorry, there was an error while trying to process the request."
//					);
//				},
//			}) 
//
//		};
//
//	},
//]);


