'use strict';

angular.module('userInfo').factory('userInfo', [function () {
    var user = [];
    return {
        userName: function () {
            return user['userName'];
        },
        credit: function () {
            return user['credit'];
        },
        userType: function () {
            return user['userType'];
        },
        init: function (a, b, c) {
            user['userName'] = a;
            user['credit'] = b;
            user['userType'] = c;
        }
    }
}]);