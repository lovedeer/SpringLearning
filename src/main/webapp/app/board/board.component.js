'use strict';
angular.module('board').component('board', {
    templateUrl: 'board/board.template.html',
    transclude: true,
    controller: ['$resource', '$location', function boardController($resource, $location) {
        var self = this;
        var Board = $resource('boards/');

        self.gotoPage = function (pageNo) {
            self.boards = Board.get({pageNo: pageNo});
        }
        self.boards = Board.get();

    }]
})