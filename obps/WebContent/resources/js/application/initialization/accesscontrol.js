
app.controller("accesscontrolCtrl", [
	"$scope",
	"$sce", "$compile",
	function($scope, $sce, $compile) {
		$scope.user = new Userlogins();
		$scope.users = [];
		$scope.URLs = [];
		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		$scope.reset = function() {
			$scope.user = new Userlogins();
		};
		$scope.save = function() {
			var mapuserpagespages = [];
			jQuery.each($scope.URLs, function(i, v) {
				if (v.checked) {
					mapuserpagespages.push({
						userpagecode: 0,
						usercode: $scope.user.usercode,
						url: v,
					});
				}
			});
			if (mapuserpagespages.length === 0) {
				alert("Please assign at least 1 page to the user. ");
				return;
			}
			jQuery.ajax({
				type: "POST",
				url: "./saveUserpages.htm",
				data: angular.toJson(mapuserpagespages),
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					if (response == 'usercodenull') {
						alert("Please assign atleast 1 User from the correct office");
						$scope.reset();

					}
					else if (response == 'urlcodenull') {
						alert("Please assign atleast 1 User from the correct office");
						$scope.reset();

					}
					else if (response == 'Mapped') {
						alert("The user has been succesfully assigned the selected page(s)");
						$scope.reset();

					}
					else if (response == 'Failed') {
						alert("Sorry, ther was an error while trying to assign the privilleges to the user.");
						$scope.reset();

					}

				},
				error: function(xhr) {

				},
			});
		};
		$scope.mappedPages = function(usercode) {
			jQuery.each($scope.URLs, function(i, v) {
				v.checked = false;
			});
			$scope.user = $scope.users.filter(obj => {
				return obj.usercode == usercode;
			})[0];

			$scope.checks();
			jQuery("html, body").animate({
				scrollTop: 0,
			}, 1000);
		};
		$scope.listUsers = function() {
			jQuery.ajax({
				type: "GET",
				url: "./listUserAndMappedPages.htm",
				// dataType: "json",
				data: "officecode=" + jQuery('#officecode').val(),
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					var scope = angular.element($("#accesscontrolCtrl")).scope();
					scope.$apply(function() {
						scope.users = response;
						scope.setDataTable(response);
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
		$scope.listURLs = function() {
			jQuery.ajax({
				type: "GET",
				url: "./listUrls.htm",
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					var scope = angular.element($("#accesscontrolCtrl")).scope();
					scope.$apply(function() {
						scope.URLs = response;
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
		$scope.checks = function() {
			var response;
			jQuery.each($scope.URLs, function(i0, v0) {
				response = false;
				jQuery.each($scope.user.mappedpages, function(i, v) {
					if (v0.urlcode === v.urlcode) {
						response = true;
						return false;
					}
				});
				v0.checked = response;
			});
		};
		$scope.setDataTable = function(obj) {
			jQuery("#displayRecords").html("");
			jQuery("#displayRecords").html("<table id='displayRecordsTable' style='width:100%' border='1'></table>");
			jQuery('#displayRecordsTable').DataTable({
				data: obj,
				columns: [
					{
						"title": "Full Name",
						"data": "fullname"
					}, {
						"title": "User Name",
						"data": "username"
					}, {
						"title": "Mobile No",
						"data": "mobileno"
					}, {
						"title": "Designation",
						"data": "designation"
					},
					{
						"title": "Action",
						"sortable": false,
						"render": function(data, type, row, meta) {
							let div = '<button style="padding:5px" class="btn btn-primary b-btn"  ng-click="mappedPages(' + row.usercode + ');mapView()">Map Pages</button>';
							return div;
						}
					}
				],
				"sortable": false,
				"columnDefs": [
					{ "width": "2%", "targets": 0 }
				],
				"bLengthChange": false,
				createdRow: function(row, data, dataIndex) {
					$compile(angular.element(row).contents())($scope);
				}
			});
		};
		$scope.mapView = function() {
			jQuery.fancybox.close();
			jQuery.fancybox({
				href: '#mapView',
				'autoSize': false,
				'transitionIn': 'elastic',
				'transitionOut': 'elastic',
				'speedIn': 600,
				'speedOut': 200,
				'overlayShow': false,
				'modal': false,
				'width': '100%'
			});
		};

		$scope.listUsers();
		$scope.listURLs();
	},
]);
