'use strict';

angular.module('myApp.view2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'view2/view2.html',
    controller: 'View2Ctrl'
  });
}])

.controller('View2Ctrl',  function($scope,$http) {
    $scope.myValue=true;
    $scope.my2Value=false;
    $scope.helo="heloo";
    $scope.submitPost = function() {
      var data = {


         "name": $scope.user.name,
        "password": $scope.user.password

      };
      $http.post("http://localhost:8080/bankserver/users/", data).success(function(data, status) {
        $scope.hello = data;
        $scope.myValue=false;
        $scope.my2Value=true;
      })
    }});