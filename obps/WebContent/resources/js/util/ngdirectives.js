app.directive("patternAlphaNumeric", function () {
    return {
        link: function (scope, element, attributes) {
            var oldVal = null;
            element.on("keyup", function (e) {
                if (!(element.val().match(PATTERN_ALPHA_NUMERIC)) &&
                        e.keyCode !== 46 // delete
                        &&
                        e.keyCode !== 8 // backspace
                        &&
                        e.keyCode !== CUT_CMD
                        &&
                        e.keyCode !== COPY_CMD
                        &&
                        e.keyCode !== PASTE_CMD) {
                    e.preventDefault();
                    element.val(oldVal);
                } else {
                    oldVal = (element.val());
                }
            });
            element.on("blur", function (e) {
            });
        }
    };
});

app.directive("onlyLetters", function () {
    return {
        link: function (scope, element, attributes) {
            var oldVal = null;
            element.on("keyup", function (e) {
                if (!(element.val().match(PATTERN_ALPHABETS)) &&
                        e.keyCode !== 46 // delete
                        &&
                        e.keyCode !== 8 // backspace
                        &&
                        e.keyCode !== CUT_CMD
                        &&
                        e.keyCode !== COPY_CMD
                        &&
                        e.keyCode !== PASTE_CMD) {
                    e.preventDefault();
                    element.val(oldVal);
                } else {
                    oldVal = (element.val());
                }
            });
            element.on("blur", function (e) {
            });
        }
    };
});

app.directive("patternAddress", function () {
    return {
        link: function (scope, element, attributes) {
            var oldVal = null;
            element.on("keyup", function (e) {
                if (!(element.val().match(PATTERN_ADDRESS)) &&
                        e.keyCode !== 46 // delete
                        &&
                        e.keyCode !== 8 // backspace
                        &&
                        e.keyCode !== (CUT_CMD)
                        &&
                        e.keyCode !== COPY_CMD
                        &&
                        e.keyCode !== PASTE_CMD) {
                    e.preventDefault();
                    element.val(oldVal);
                } else {
                    oldVal = (element.val());
                }
            });
            element.on("blur", function (e) {
            });
        }
    };
});

app.directive("patternEmail", function () {
    return {
        link: function (scope, element, attributes) {
            var oldVal = null;
            element.on("keyup", function (e) {
                if (!(element.val().match(PATTERN_EMAIL)) &&
                        e.keyCode !== 46 // delete
                        &&
                        e.keyCode !== 8 // backspace
                        &&
                        e.keyCode !== CUT_CMD
                        &&
                        e.keyCode !== COPY_CMD
                        &&
                        e.keyCode !== PASTE_CMD) {
                    e.preventDefault();
                    element.val(oldVal);
                } else {
                    oldVal = (element.val());
                }
            });
            element.on("blur", function (e) {
            });
        }
    };
});

app.directive('validNumber', function () {
    return {
        require: '?ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
            if (!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function (val) {
                if (angular.isUndefined(val)) {
                    var val = '';
                }
                var clean = val.replace(/[^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function (event) {
                if (event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

app.directive('loading', ['$http', function ($http) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        scope.isLoading = function () {
          return $http.pendingRequests.length > 0;
        };
        scope.$watch(scope.isLoading, function (value) {
          if (value) {
            element.removeClass('ng-hide');
          } else {
            element.addClass('ng-hide');
          }
        });
      }
    };
}]);