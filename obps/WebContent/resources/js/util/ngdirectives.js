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
      element.on("blur", function (e) {});
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
      element.on("blur", function (e) {});
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
      element.on("blur", function (e) {});
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
        if (!modelValue) return true;
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

app.directive("loading", [
  "$http",
  function ($http) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        scope.isLoading = function () {
          return $http.pendingRequests.length > 0;
        };
        scope.$watch(scope.isLoading, function (value) {
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
