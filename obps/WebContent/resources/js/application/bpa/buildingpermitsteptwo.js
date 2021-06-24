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
        if (success.code == '201') {
          $scope.serverResponseSuccess = true;
          try {
            $scope.serverMsg += "\nNext Process: " + success.nextProcess.value;
            if (success.nextProcess != null && success.nextProcess.key != null && success.nextProcess.key != '') {
              $timeout(() => {
                let url = success.nextProcess.key + "?applicationcode=" + BPA.applicationcode;
                $window.location.href = url;
              }, 2500);
            } else
              $timeout(() => { $window.location.reload(); }, 2500);
          } catch (e) { }
        } else {
          $scope.serverResponseFail = true;
        }
      }, (error) => {
        $scope.serverMsg = error.msg;
        $scope.serverResponseError = true;
      });
    }
  }
]);