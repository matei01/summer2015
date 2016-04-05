'use strict';

angular.module('myApp.view1', ['ngRoute','ngResource'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function($scope,$http,Users) {
      $scope.myValue=true;
        $scope.accountss=[];
        $scope.accountValue=false;
        $scope.my2Value=false;
        $scope.value3=false;
        $scope.value5=false;
        $scope.value6=false;
        $scope.value7=false;
        $scope.value8==10;
        $scope.balance=0;
        $scope.valuek=false;
      $scope.users={};



      $scope.submitLogin= function(user){
        Users.query(function(data){
          angular.forEach(data,function(dat){
          if((dat.name==user.name)&&(dat.password==user.password)){
              $scope.myValue=false;
              $scope.accountValue=true;
              Users.query({userr:user.name},function(counts){
                  angular.forEach(counts,function(account){
                      $scope.accountss.push(account);
                  });
              });

          }
          });
            if($scope.myValue==true){
                $scope.my2Value=true;
                $scope.myValue=false;
            }
        });
      };


    $scope.createAccount= function(){
        $scope.value3=true;
        $scope.accountValue=false;
    }


    $scope.submitAccount= function(user,account){
        Users.newAc({userr:user.name},account);
        $scope.accountss.push(account);
        $scope.value3=false;
        $scope.accountValue=true;
    }

    $scope.withdrawAccount= function(){
        $scope.value4=true;
        $scope.value3=false;

    }

    $scope.submitWithdrawal=function(user,account){
        if($scope.value6==true){
            account.amount=-account.amount;
        }
        Users.query({userr:user.name},function(counts){
            angular.forEach(counts,function(accountt){
                if(accountt.name==account.name){
                    accountt.amount-=account.amount;
                    Users.update({userr:user.name}, accountt);
                    $scope.value4=false;

                }
            });
            if($scope.value4==true){
                $scope.value5=true;
            }
            if($scope.value6==true){
                $scope.value6=false;
            }
        });
        angular.forEach($scope.accountss,function(ac){
            if(ac.name==account.name){
                ac.amount=ac.amount-account.amount;
            }
        });
    }

    $scope.depositAccount=function(user,account){
        $scope.value6=true;

    }

    $scope.transferAccount=function(){
        $scope.value7=true;
    }

    $scope.submitTransfer=function(user,account2,account3){
        $scope.value8==11;
        $scope.submitWithdrawal(user,account2);
        account3.amount=-account2.amount;
        $scope.submitWithdrawal(user,account3);
        $scope.value7=false;
    }

    $scope.getBalance= function(user){
        $http.get('http://www.localhost:8080/bankserver/users/'+user.name+'/balance').success(function(data){
            $scope.balance = data;
        });
        $scope.valuek=true;
    }


});
