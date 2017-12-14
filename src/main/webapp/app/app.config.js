'use strict';

angular
    .module('app').controller('appCtrl', ['$scope', '$cookies', function ($scope, $cookies) {
    if (!$cookies.get('show')) {
        window.location.href = 'login.html';
    }
    var self = this;
    self.show = $cookies.get('show');
    $scope.$on('showEvent', function () {
        $scope.$broadcast('topShow');
        self.show = true;
    });
}])
// .factory('MyInterceptor', ['$cookies', '$q', function ($cookies, $q) {
//     return {
//         // 可选，拦截成功的请求
//         request: function (config) {
//             if (!$cookies.get('show')) {
//                 config.url = 'http://127.0.0.1:8080/bbs/login.html';
//                 window.location.href = 'login.html';
//             }
//            else  return config || $q.when(config);
//         }
//     }
// }])
    .config(['$locationProvider', '$routeProvider', '$httpProvider',
        function config($locationProvider, $routeProvider, $httpProvider) {
            // $httpProvider.interceptors.push('MyInterceptor');
            $locationProvider.hashPrefix('!');
            $routeProvider
                .when('/board', {
                    template: '<board></board>'
                })
                .when('/board/:boardId/topic', {
                    template: '<topic></topic>'
                })
                .when('/board/:boardId/topic/:topicId/post', {
                    template: '<post></post>'
                })
                .otherwise('/board');
        }]);