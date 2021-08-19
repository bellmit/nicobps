/*COMMAND KEY*/
const CTRL_KEY = 17;
const DELETE_KEY = 46;
const BACKSPACE_KEY = 8;
const CUT_CMD = 17 && 88;
const COPY_CMD = 17 && 67;
const PASTE_CMD = 17 && 86;

const PATTERN_ADDRESS = /^[0-9a-zA-Z\ ()'.,]+$/;
app.directive("patternAddress", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      let oldVal = "";
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ADDRESS) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
          ngModelCtrl.$setViewValue("");
          ngModelCtrl.$render();
        } else {
          oldVal = element.val();
          ngModelCtrl.$setViewValue(oldVal);
          ngModelCtrl.$render();
        }
      });
      element.on("blur", function (e) { });
    },
  };
});

app.directive("patternAlpha", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      var oldVal = null;
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ALPHABETS) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
          element.val(oldVal);
        } else {
          oldVal = element.val();
        }

        ngModelCtrl.$setViewValue(oldVal);
        ngModelCtrl.$render();
      });
      element.on("blur", function (e) { });
    },
  };
});

app.directive("patternAlphaNumeric", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      var oldVal = null;
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ALPHA_NUMERIC) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
        } else {
          oldVal = element.val();
        }

        ngModelCtrl.$setViewValue(oldVal);
        ngModelCtrl.$render();
      });
      element.on("blur", function (e) { });
    },
  };
});


app.directive("patternEmail", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attrs, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      let oldVal = "";
      ngModelCtrl.$validators.validate = (modelValue, viewValue) => {
    	  console.log("modelValue: ",modelValue);
    	  console.log("modelValue == '': ",(modelValue == ''));
        if (!modelValue || modelValue == '') {ngModelCtrl.$error.invalid = false; return true;}
        if (!PATTERN_EMAIL.test(modelValue)) {
          ngModelCtrl.$error.invalid = true;
          return false;
        } else {
          ngModelCtrl.$error.invalid = false;
          return true;
        }
      };
      element.on("blur", function (e) {
        if (
          !element.val().match(PATTERN_EMAIL) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
          ngModelCtrl.$setViewValue("");
          ngModelCtrl.$render();
        } else {
          oldVal = element.val();
          ngModelCtrl.$setViewValue(oldVal);
          ngModelCtrl.$render();
        }
      });

      element.bind("keypress", function (event) {
        if (event.keyCode === 32) {
          event.preventDefault();
        }
      });
    },
  };
});


app.directive("patternMobile", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attrs, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      // console.log("ngModelCtrl : ", ngModelCtrl);
      ngModelCtrl.$validators.validate = (modelValue = "", viewValue = "") => {
        if (!modelValue) return true;
        if (!PATTERN_MOBILE.test(modelValue)) {
          ngModelCtrl.$error.invalid = true;
          return false;
        } else {
          ngModelCtrl.$error.invalid = false;
          return true;
        }
      };
    },
  };
});

app.directive("patternNumber", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attrs, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      ngModelCtrl.$parsers.push(function (val) {
        if (angular.isUndefined(val)) {
          var val = "";
        }
        var clean = val.replace(/[^0-9]+/g, "");
        if (val !== clean) {
          ngModelCtrl.$setViewValue(clean);
          ngModelCtrl.$render();
        }
        return clean;
      });

      element.bind("keypress", function (event) {
        if (event.keyCode === 32) {
          event.preventDefault();
        }
      });
    },
  };
});

app.directive("patternPincode", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attrs, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      // console.log("ngModelCtrl : ", ngModelCtrl);
      ngModelCtrl.$validators.validate = (modelValue = "", viewValue = "") => {
        if (!modelValue) return true;
        if (!PATTERN_PINCODE.test(modelValue)) {
          ngModelCtrl.$error.invalid = true;
          return false;
        } else {
          ngModelCtrl.$error.invalid = false;
          return true;
        }
      };
    },
  };
});

app.directive("fileModel", [
  "$parse", "$timeout",
  function ($parse, $timeout) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind("change", function (event) {
          let file = element[0].files[0];
          if (PATTERN_FILE_ENC.test(file.type)) {
            element[0].nextElementSibling.innerHTML = '';
            scope.$apply(function () {
              var reader = new FileReader();
              reader.readAsDataURL(element[0].files[0]);
              reader.onload = function () {
                modelSetter(scope, reader.result);
              };
            });
          } else {
            $(this).val(null);
            element[0].nextElementSibling.innerHTML = '<br/>Invalid enclosure: Only jpeg/jpg/png/pdf allowed';
            $timeout(() => {
              $timeout(() => {
                element[0].nextElementSibling.innerHTML = 'Required';
              }, 5000);
            }, 5000);
            // alert("Invalid enclosure: Only jpeg/jpg/png/pdf allowed");
          }
        });
      },
    };
  },
]);

app.directive("imageModel", [
  "$parse",
  function ($parse) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        var model = $parse(attrs.imageModel);
        var modelSetter = model.assign;
        element.bind("change", function (event) {
          let file = element[0].files[0];
          if (file.type.match(PATTERN_FILE_IMG)) {
            scope.$apply(function () {
              var reader = new FileReader();
              reader.readAsDataURL(element[0].files[0]);
              reader.onload = function () {
                modelSetter(scope, reader.result);
              };
            });
          } else {
            $(this).val(null);
            alert("Invalid image: Only jpeg/jpg or png allowed");
          }
        });
      },
    };
  },
]);

app.directive("loading", [
  "$http",
  function ($http) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        console.log("loading");
        scope.isLoading = function () {
          console.log("1: loading");
          return $http.pendingRequests.length > 0;
        };
        scope.$watch(scope.isLoading, function (value) {
          console.log("2: loading: ", value);
          if (value) {
            element.removeClass("ng-hide");
          } else {
            element.addClass("ng-hide");
          }
        });
      },
    };
  },
]);

/* app.directive('loading', function () {
  return {
    restrict: 'E',
    replace: true,
    template: '<p><img src="resources/images/c-processing.gif" alt="LOADING...."/></p>',
    link: function (scope, element, attr) {
      console.log("Loading");
      scope.$watch('loading', function (val) {
        val = val ? $(element).show() : $(element).hide();
      });
    }
  }
}); */

/*

app.directive("testFileModel", [
  "$parse", "$timeout",
  function ($parse, $timeout) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        var model = $parse(attrs.testFileModel);
        var modelSetter = model.assign;

        element.bind("change", function (event) {
          let file = element[0].files[0];
          if(file.type != null){
            element[0].nextElementSibling.innerHTML = '';
            scope.$apply(function () {
              var reader = new FileReader();
              reader.readAsDataURL(element[0].files[0]);
              reader.onload = function () {
                modelSetter(scope, reader.result);
              };
            });
          }
        });
      },
    };
  },
]);

*/