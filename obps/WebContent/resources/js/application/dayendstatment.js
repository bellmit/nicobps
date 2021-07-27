jQuery(document).ready(function () {

    jQuery("#fromDate").hide();
    jQuery("#toDate").hide();

});

var app = angular.module("dayendstat", []);

app.controller('dayendstat', function ($scope, $compile, $timeout) {
	
	$scope.dateFormat = "dd-mm-yy";
	
	$scope.from = jQuery("#fromentrydate").datepicker({
		defaultDate: "0",
		changeMonth: true,
		dateFormat: $scope.dateFormat,
		maxDate: "0"
	}).on("change", function () {
		$scope.to.datepicker("option", "minDate", $scope.getDate(this));
	});

	$scope.to = jQuery("#toentrydate").datepicker({
		defaultDate: "0",
		changeMonth: true,
		dateFormat: $scope.dateFormat,
		maxDate: "0"
	}).on("change", function () {
		$scope.from.datepicker("option", "maxDate", $scope.getDate(this));
	});
$scope.getDate = function(element) {

		try {
			$scope.date = $.datepicker.parseDate($scope.dateFormat, element.value);
		} catch (error) {
			$scope.date = null;
		}

		return $scope.date;
	}
});

function printdayeendstat(officecode){	


	var from = jQuery("#fromentrydate").val();
	var to = jQuery("#toentrydate").val();
		
	if(from==""||!from.match(PATTERN_DATE)){
	    jQuery("#fromDate").show();
        jQuery("#fromDate").fadeOut(3000);
		
 false;
	} 
	if(to=="" || !to.match(PATTERN_DATE)){
	    jQuery("#toDate").show();
        jQuery("#toDate").fadeOut(3000);
		return false;
	}
	
	var params="status=3&officecode="+officecode+"&fromdate="+from+"&todate="+to;		
	window.open("./Report?"+params);
}

