/**
 * @author Decent Khongstia
 */

app.controller("CommonCtrl", [
  "$scope",
  "$http",
  "$timeout",
  "$compile",
  "$window",
  "commonInitService",
  "bpaService",
  function ($scope, $http, $timeout, $compile, $window, CIS, BS) {
    console.log("BPA-STEP II");
    console.log("APPCODE: ", APPCODE);
    
    $scope.BPA = new BPA();

    $scope.save = () => {
      let BPA = {}, valid = false;
      $scope.BPA.applicationcode = APPCODE;
      BPA = $scope.BPA.init($scope.BPA);

      CIS.save("POST", "./savebpasteptwo.htm", BPA, (success) => {
        $scope.serverMsg = success.msg;
        if(success.code == '201'){
          $scope.serverResponseSuccess = true;
          try{
            $scope.serverMsg += "\nNext Process: "+success.nextProcess.value;
            $timeout(() => {
              let url = success.nextProcess.key+"?edcrnumber="+BPA.applicationcode;
              $window.location.href = url;
            },4500);
          }catch(e){}
          
        }else{
          $scope.serverResponseFail = true;
        }
      }, (error) => {
        $scope.serverMsg = error.msg;
        $scope.serverResponseError = true;
      });
    }
	}
]);