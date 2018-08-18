//******** FullScreen img ****** 

var formApp = angular.module('FormApp', []);-
formApp.controller('FormController', [
  '$scope', '$window', '$http',
  function($scope, $window, $http) {
    $scope.messages = [];
    // AngularJS will populate this object with input
    // values based on the data-ng-model mappings.
    $scope.data = {}; 
    $scope.submit = function() {
    $http({
      method: 'POST', url: './form.do',
      data: $scope.data
    }).
      success(function(data, status, headers, config) {
        $window.location.replace('./confirm.html');
      }).
      error(function(data, status, headers, config) {
        if(status == 400) {
          $scope.messages = data;
        } else {
          alert('Unexpected server error.');
        }
      });
  };
}]);


