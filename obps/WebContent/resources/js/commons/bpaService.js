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
      getBpaApplicationDetails: async function (callback, data = "") {
        CUS.getData('./getBpaApplicationDetails.htm', data, callback);
      },
      getBpaApplicationDetailsV2: async function (callback, data = "") {
        CUS.getData('./getBpaApplicationDetailsV2.htm', data, callback);
      },
      getBpaApplicationFee: async function (callback, data = "") {
        CUS.getData('./getBpaApplicationFee.htm', data, callback);
      },
      getBpaPermitFee: async function (callback, data = "") {
        CUS.getData('./getBpaPermitFee.htm', data, callback);
      },
      getCurrentProcessTaskStatus: async function (callback, data = "") {
        CUS.getData('./getCurrentProcessTaskStatus.htm', data, callback);
      },
      getEdcrDetails: async function (callback, data = "") {
        CUS.getData('./getEdcrDetails.htm', data, callback);
      },
      getEdcrDetailsV2: async function (callback, data = "") {
        CUS.getData('./getEdcrDetailsV2.htm', data, callback);
      },
      getEdcrDetailsV3: async function (callback, data = "") {
        CUS.getData('./getEdcrDetailsV3.htm', data, callback);
      },
      getOfficePaymentMode: async function (callback, data = "") {
        CUS.getData('./getOfficePaymentMode.htm', data, callback);
      },
      listAppScrutinyDetailsForBPA: async function (callback, data = "") {
        CUS.getData("./listAppScrutinyDetailsForBPA.htm", data, callback);
      },
      listBPApplications: async function (callback, data = "") {
        CUS.getData("./listbpapplications.htm", data, callback);
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
      listSiteReportDetails: async function (callback, data = "") {
        CUS.getData('./listSiteReportDetails.htm', data, callback);
      },
      test: async function (callback, data = "") {
        CUS.getData("./listTest.htm", data, callback);
      },
      /* ACTION */
      getFloorName: function (data = null) {
        let name = "";
        switch (data) {
          case -3: name = "Basement Level 3";
            break;
          case -2: name = "Basement Level 2";
            break;
          case -1: name = "Basement Level 1";
            break;
          case 0: name = "Ground Floor";
            break;
          case 1: name = "First Floor";
            break;
          case 2: name = "Second Floor";
            break;
          case 3: name = "Third Floor";
            break;
          case 4: name = "Fourth Floor";
            break;
        }
        return name;
      },
      getFloorSubOccupancy: function (data = "") {
        let occupanies = [];
        try {
          data.forEach((o, x) => {
            if (o.typeHelper != null && o.typeHelper.subtype != null && o.typeHelper.subtype.name != null) {
              occupanies.push(o.typeHelper.subtype.name);
            } else if (o.typeHelper != null && o.typeHelper.type != null && o.typeHelper.type.name != null) {
              occupanies.push(o.typeHelper.type.name);
            }
          });
        } catch (e) {

        }
        return occupanies.join(",");
      },
      getFloorTotalArea: (data) => {
        let floorArea = null;
        try {
          data.forEach((o, x) => {
            floorArea = floorArea + parseFloat(o.floorArea);
          });
        } catch (e) {

        }
        return floorArea;
      },
      getFloorTotalBuiltUpArea: (data) => {
        let builtUpArea = null;
        try {
          data.forEach((o, x) => {
            builtUpArea = builtUpArea + parseFloat(o.builtUpArea);
          });
        } catch (e) {

        }
        return builtUpArea;
      },
      detectMimeType: (data) => {
        console.log("MimeType: ", FileMimeType[data.toUpperCase().substr(0, 5)]);
        return FileMimeType[data.toUpperCase().substr(0, 5)];
      }
    };
  },
]);
