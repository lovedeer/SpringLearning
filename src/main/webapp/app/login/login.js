angular.module("loginApp", []).controller("loginCtrl",  function () {
    this.reset = function () {
        this.username = "";
        this.password = "";
    };
    this.error = false;

    this.login = function () {

    };
});