
/* @author Decent Khongstia*/
app.service("commonUtilService", [
  "$http",
  function ($http) {
    console.log("commonUtilService");
    return {
      getData: async (endpoint, data, callback) => {
        var url = endpoint;
        if (data != null && data != '')
          url = endpoint.concat("?param=").concat(data);
        $http.get(url).success(function (response, status, headers, config) {
          callback(response);
        });
      },
      /* 
      postData: async (endpoint, data, successCallback, errorCallback) => {
        var url = endpoint;
        $http.post(url, data).then((response) => {
          successCallback(response);
        }, (errResponse) => {
          errorCallback(errResponse);
        })
      }
       */
    };
  },
]);

app.service("bpaService", [
  "$http",
  "commonUtilService",
  function ($http, CUS) {
    console.log("bpaService");
    return {
      getBpaApplicationFee: async function (callback, data = "") {
        CUS.getData('./getBpaApplicationFee.htm', data, callback);
      },
      getBpaPermitFee: async function (callback, data = "") {
        CUS.getData('./getBpaPermitFee.htm', data, callback);
      },
      getEdcrDetails: async function (callback, data = "") {
        CUS.getData('./getEdcrDetails.htm', data, callback);
      },
      getOfficePaymentMode: async function (callback, data = "") {
        CUS.getData('./getOfficePaymentMode.htm', data, callback);
      },
      listAppScrutinyDetailsForBPA: async function (callback, data = "") {
        CUS.getData("./listAppScrutinyDetailsForBPA.htm", data, callback);
      },
      listOfficelocations: async function (callback, data = "") {
        CUS.getData("./listOfficelocations.htm", data, callback);
      },
      listOwnershiptypes: async function (callback, data = "") {
        CUS.getData("./listOwnershiptypes.htm", data, callback);
      },
      listRelationshiptypes: async function (callback, data = "") {
        CUS.getData("./listRelationshiptypes.htm", data, callback);
      },
      listSalutations: async function (callback, data = "") {
        CUS.getData("./listSalutations.htm", data, callback);
      },
      test: async function (callback, data = "") {
        CUS.getData("./listTest.htm", data, callback);
      },
    };
  },
]);
