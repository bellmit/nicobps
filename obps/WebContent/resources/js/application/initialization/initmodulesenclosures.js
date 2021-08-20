

app.controller("initmodulesenclosuresCtrl", [
	"$scope",
	"$sce", "$compile", "$timeout",
	function($scope, $sce, $compile, $timeout) {

		$scope.module = new Modules();

		$scope.modules = [];
		$scope.processes = [];
		$scope.processesList = [];
		$scope.modulesList = [];
		$scope.enclosure = [];
		$scope.mappedenclosures = [];
		$scope.officecode = "0";
		$scope.licenseetypecode = "0";
		$scope.processcode = "0";
		$scope.trustHTML = function(post) {
			return $sce.trustAsHtml(post);
		};
		$scope.reset = function() {
			$scope.module = new Modules();
			
		};

		$scope.save = function() {

			var mapuserpagespages = [];
			jQuery.each($scope.enclosures, function(i, v) {
				if (v.checked) {

					mapuserpagespages.push({

						modulecode: $scope.modulecode,
						enclosurecode: v,
						officecode: $scope.officecode,
						processcode: $scope.processcode,
						licenseetypecode: $scope.licenseetypecode,
					});
				}
			});
			if (mapuserpagespages.length === 0) {
				alert("Select atleast one Enclosure");
				return;
			}
			jQuery.ajax({
				type: "POST",
				url: "./saveModuleEnclosures.htm",
				data: angular.toJson(mapuserpagespages),
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					if (response == "enclosurecodenull")
						alert("Please Select atleast 1 enclosure");
					else if (response == "modulecodenull")
						alert("Please Select atleast 1 module");
					else if (response == "officecodenull")
						alert("Please Select Office ");
					else if (response == "processcodenull")
						alert("Please Select Process ");
					else if (response == "licenseetypecodenull")
						alert("Please Select License ");
					else if (response == 'Mapped') {
						alert("Mapped");
						location.reload();
					} else if (response == 'Failed') {
						alert("Failed");
						location.reload();
					}


					$scope.reset();
					//          $scope.listModules();
				},
				error: function(xhr) {
					alert(xhr.status + " = " + xhr);
					alert(
						"Sorry, there was an error while trying to process the request."
					);
				},
			});
		};

		$scope.mappedEnclosures = function(processcode) {




			jQuery.each($scope.enclosures, function(i, v) {

				v.checked = false;
			});
			console.log($scope.enclosure = $scope.enclosures.filter(obj => {
				return obj.enclosurecode == $scope.enclosures.enclosurecode;
			})[0])


			//    	$scope.module =$scope.modules.filter(obj=>{
			//    		return obj.modulecode==modulecode;
			//    	})[0];

			$scope.checks();
			jQuery("html, body").animate({
				scrollTop: 0,
			}, 1000);
		};





		$scope.listModules = function() {


			jQuery.ajax({
				type: "GET",
				url: "./listModulesAndEnclosures.htm",
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {

					var scope = angular.element($("#initmodulesenclosuresCtrl")).scope();

					scope.$apply(function() {




						scope.modules = response;

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


		$scope.listEnclosures = function() {

			jQuery.ajax({
				type: "GET",
				url: "./listEnclosures.htm",
				// dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(response) {
					var scope = angular.element($("#initmodulesenclosuresCtrl")).scope();
					scope.$apply(function() {
						scope.enclosures = response;

					});
					//        $scope.checks();
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
			console.log('checks 1' + $scope.enclosures[0].enclosurecode);
			console.log('checks 2' + $scope.mappedenclosures[0].enclosurecode);
			var response;

			jQuery.each($scope.enclosures, function(i0, v0) {

				response = false;
				jQuery.each($scope.mappedenclosures, function(i, v) {
					console.log('enclosurecode' + v.enclosurecode)
					if (v0.enclosurecode === v.enclosurecode) {
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
						"title": "Sl No",
						"data": "modulecode"
					},
					{
						"title": "Module Name",
						"data": "modulename"
					},

					{
						"title": "Action",
						"sortable": false,
						"render": function(data, type, row, meta) {
							let div = '<button style="padding:5px" ng-click="mappedEnclosures(' + row.modulecode + ')">Map Enclosures</button>';
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

		$scope.listProcesses = function(modulecode) {


			if ($scope.modulecode != 0) {
				jQuery.ajax({
					type: "GET",
					url: "./listProcesses.htm",
					// dataType: "json",
					data: "modulecode=" + modulecode,
					contentType: "application/json; charset=utf-8",
					success: function(data) {
						$timeout(() => {
							console.log(data);
							$scope.processesList = data.processesList;

						}, 0);


					},
					error: function(xhr) {
						alert(xhr.status + " = " + xhr);
						alert(
							"Sorry, there was an error while trying to process the request."
						);
					},
				});

			}





		};


		$scope.listEnclosures();

		$scope.getMappedEnclosures = function(processcode) {

			jQuery.each($scope.enclosures, function(i, v) {

				v.checked = false;
			});
			var params = {
				"processcode": processcode,
				"officecode": $scope.officecode,
				"modulecode": $scope.modulecode,
				"licenseetypecode": $scope.licenseetypecode
			};
			console.log(params)


			jQuery.ajax({
				type: "GET",
				url: "./listModulesAndEnclosures.htm",
				//	dataType: "json",
				contentType: "application/json; charset=utf-8",
				data: params,
				success: function(response) {
					$scope.mappedenclosures = response;
					//				console.log("response:" + $scope.mappedenclosures[0].enclosurecode);
					console.log("length" + response.length)
					if (response.length != 0) {
						$timeout(() => {
							$scope.checks();
						}, 0);
					}


				},

			});



			//		    $scope.checks();
			// $scope.mappedEnclosures();
		};



	},


]);
