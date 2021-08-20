var app = angular.module("CommonApp", []);

app.controller('userwardsCtrl', function($scope, $compile, $timeout) {

	/*$scope.init = function() {
		$scope.showWard = false;
	};*/

	$scope.usersList = [];
	$scope.userwards = [];
	$scope.wards = [];
	$scope.user = new OfficeLocations();
	$scope.officecode = 0;
	$scope.listUsers = function() {

		var param = {
			"officecode": $scope.officecode,

		};
		jQuery.ajax({
			url: "./listUsers.htm",
			dataType: 'json',
			data: param,
			type: "POST",
			success: function(data) {

				$timeout(() => {
					$scope.usersList = data.usersList;

				}, 0);


			},
			error: function(request, status, error) {
				//alert(request.responseText);
			}
		});
		$scope.listUsersWards();
	}



	$scope.listWards = function(officecode) {
	
		var param = {
			"officecode": $scope.officecode,

		};
		if ($scope.officecode != 0) {
			jQuery.ajax({
				type: "GET",
				url: "./listWards.htm",
				// dataType: "json",
				data: param,
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					console.log(response)

					$timeout(() => {
						$scope.wards = response;

					}, 0);

				},
				error: function(xhr) {
					alert(xhr.status + " = " + xhr);
					alert(
						"Sorry, there was an error while trying to process the request."
					);
				},
			});
			$scope.listUsers();
		}


	};


	$scope.checks = function() {
		var response;
		console.log($scope.wards)
		console.log($scope.user)
		jQuery.each($scope.wards, function(i0, v0) {
			response = false;
			jQuery.each($scope.user.mappedofficelocations, function(i, v) {
				if (v0.locationcode === v.locationcode) {
					response = true;
					return false;
				}
			});
			v0.checked = response;
		});
	};

	$scope.mappedWards = function(usercode) {
		console.log("usercode:::", usercode)
		jQuery.each($scope.wards, function(i, v) {
			v.checked = false;
		});
		console.log("return user:::", $scope.user = $scope.userwards.filter(obj => {
			return obj.usercode == usercode;
		})[0]);
		$scope.user = $scope.userwards.filter(obj => {
			return obj.usercode == usercode;
		})[0];

		$scope.checks();
		jQuery("html, body").animate({
			scrollTop: 0,
		}, 1000);
	};


	$scope.listUsersWards = function() {

		var param = {
			"officecode": $scope.officecode,


		};
		jQuery.ajax({
			type: "GET",
			url: "./listUserAndMappedWards.htm",
			// dataType: "json",
			data: param,
			contentType: "application/json; charset=utf-8",
			success: function(response) {
				var scope = angular.element($("#userwardsCtrl")).scope();
				scope.$apply(function() {
					//					console.log(response)
					scope.userwards = response;
				});
			},
			error: function(xhr) {
				alert(xhr.status + " = " + xhr);
				alert(
					"Sorry, there was an error while trying to process the request."
				);
			},
		});
	};


	//map wards
	$scope.save = function() {
		//		console.log("map wards::", $scope.wards)
		//console.log($scope.officecode)
		if (jQuery('#officecode').val() === "0") {
			alert("Select Office");
			return;
		}
		if (jQuery('#usercode').val() == "") {
			alert("Select User");
			return;
		}
		var mapuserwards = [];
		jQuery.each($scope.wards, function(i, v) {
			if (v.checked) {
				mapuserwards.push({
					usercode: $scope.usercode,
					ward: v,
				});
			}
		});


		if (mapuserwards.length === 0) {
			alert("Select atleast one Ward");
			return;
		}
		jQuery.ajax({
			type: "POST",
			url: "./saveUserWards.htm",
			data: angular.toJson(mapuserwards),
			// dataType: "json",
			contentType: "application/json; charset=utf-8",
			success: function(response) {
				if (response == 'nodata') {
					alert("Failed! No data");
					location.reload();
				} else
					if (response == 'usercodenull') {
						alert("Please Select atleast 1 User from the correct office");
						return;

					}
					else if (response == 'locationcodenull') {
						alert("Please Select atleast 1 Ward");
						return;

					}
					else
						if (response == 'Mapped') {
							alert("Mapped");
							location.reload();
							/*$scope.reset();*/

						}
						else if (response == 'Failed') {
							alert("Failed");
							location.reload();

						}

			},
			error: function(xhr) {

			},
		});
	};

	$scope.reset = function() {
			$scope.officecode = 0;
			$scope.usercode = "";
		jQuery("#officecode").val(0);
		jQuery("#usercode").val('');
	
		jQuery.each($scope.wards, function(i, v) {
			v.checked = false;
		});
			$scope.wards = [];

	};


});//end


