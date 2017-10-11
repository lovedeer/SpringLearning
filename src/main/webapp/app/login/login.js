angular.module("loginApp", ['ngResource']).controller("loginCtrl", ['$resource', '$location', function ($resource, $location) {
    this.reset = function () {
        this.username = "";
        this.password = "";
    };
    this.error = false;
    var self = this;
    var User = $resource('login/username/:username/password/:password');

    this.login = function () {
        var user = User.get({username: this.username, password: this.password}, function () {
            if (user.userName == null) {
                self.error = true;
            }
            else {
               window.location="index.html";
            }
        });
    };
}]);