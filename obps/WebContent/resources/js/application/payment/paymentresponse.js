var app = angular.module("CommonApp", []);
app.controller('responseController', function($scope) {

	$scope.init = function(transactioncode) {
		console.log("transactioncode :: " + transactioncode);
		$scope.transactioncode = transactioncode;

	};

	$scope.generate = function() {

		var params = "transactioncode=" + $scope.transactioncode;
		window.open("generateReceipt.htm?" + params);

	};

	$scope.getClass = function(status) {
		if (status == "0300")
			return "bg-success text-white"
		else
			return "bg-warning text-black";
	}
});



