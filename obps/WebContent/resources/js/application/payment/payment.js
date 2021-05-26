var app = angular.module("CommonApp", []);
app.controller('paymentController', function($scope) {

	$scope.init = function(applicationcode, feecode, feeamount) {
		$scope.applicationcode = applicationcode;
		$scope.feecode = feecode;
		$scope.feeamount = feeamount;
		

		$scope.submitDetails = () => {
			var flag = confirm(" You are about to get Redirect to BillDesk Payment URL.");
			if (!flag) { return false; }
			jQuery('#paymentForm').submit();

		};


	};

});



