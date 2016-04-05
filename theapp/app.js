'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myApp.view1',
  'myApp.view2',
  'myApp.version',
  'ngResource'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/'});
}])
.factory("Users", function($resource){
  return $resource("http://www.localhost:8080/bankserver/users/:userr",{userr:'@userr'},
      {newAc: { method: 'POST' },
      update:{method: 'PUT'}});
});