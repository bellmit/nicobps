var app = angular.module("CommonApp", []);
app.controller('responseController', function($scope) {

	$scope.init = function(transactioncode) {
		console.log("transactioncode :: " + transactioncode);
		$scope.transactioncode = transactioncode;

	};



	$scope.getClass = function(status) {
		if (status == "0300")
			return "bg-success text-white"
		else
			return "bg-warning text-black";
	}


	$scope.print = function() {
		console.log("print");

		window.open("./Report?status=1&transactioncode=" + $scope.transactioncode);


	}
});



