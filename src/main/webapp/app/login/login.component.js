'use strict';
angular.module('login').controller('loginCtrl', ['$resource', '$cookies', '$location', function ($resource, $cookies, $location) {
    var self = this;
    self.reset = function () {
        self.username = "";
        self.password = "";
        self.error = "";
    };
    self.error = "";

    var User = $resource('login/username/:username/password/:password');

    this.login = function () {
        if (!this.username || !this.password)
            return;
        var user = User.get({username: this.username, password: this.password}, function () {
            if (user.userName === undefined) {
                self.error = "wrong username or password!";
            }
            else {
                var expireDate = new Date();
                expireDate.setTime(expireDate.getTime() + 1000 * 5);
                $cookies.put('userName', user.userName);
                $cookies.put('credit', user.credit);
                $cookies.put('userType', user.userType);
                $cookies.put('show', 'true');
                // $location.path('/board');
                window.location.href = 'app.html#!/board';
            }
        });
    };
}]).directive('loginDrag', ['$document', function ($document) {
    return {
        restrict: 'AE',
        link: function (scope, element, attr) {
            var startX = 0, startY = 0, x = 0, y = 0;

            var ele = element.parent("div");
            element.css({
                //          position: 'relative',
                cursor: 'pointer'
            });
            ele.css({
                position: 'relative',
                cursor: 'pointer'
            });

            element.on('mousedown', function (event) {
                // Prevent default dragging of selected content
                event.preventDefault();
                startX = event.pageX - x;
                startY = event.pageY - y;
                $document.on('mousemove', mousemove);
                $document.on('mouseup', mouseup);
            });

            function mousemove(event) {
                y = event.pageY - startY;
                x = event.pageX - startX;
                ele.css({
                    top: y + 'px',
                    left: x + 'px'
                });
            }

            function mouseup() {
                $document.off('mousemove', mousemove);
                $document.off('mouseup', mouseup);
            }
        }
    };
}]);