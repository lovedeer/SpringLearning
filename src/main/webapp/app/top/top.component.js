'use strict';
angular.module('top').component('top', {
    templateUrl: 'top/top.template.html',
    controller: ['$scope', '$cookies',  function ($scope, $cookies) {
        var self = this;
        self.show = false;
        self.userName = $cookies.get('userName');
        self.credit = $cookies.get('credit');
        self.userType = $cookies.get('userType');
        $scope.$on('topShow', function () {
            // self.userName = $rootScope.userName;
            // self.credit = $rootScope.credit;
            // self.userType = $rootScope.userType;
            self.userName = $cookies.get('userName');
            self.credit = $cookies.get('credit');
            self.userType = $cookies.get('userType');
            self.show = true;
        });
    }]
});