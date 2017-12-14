'use strict';
angular.module('topic').component('topic', {
    templateUrl: 'topic/topic.template.html',
    transclude: true,
    controller: ['$resource', '$routeParams', '$cookies', function postController($resource, $routeParams, $cookies) {
        var self = this;
        self.userType = $cookies.get('userType');
        self.ids = [];
        self.select = function () {
            self.ids = [];
            var selector = $("input[name='topicId']");
            selector.each(function (index, element) {
                if ($(element).prop('checked'))
                    self.ids.push($(element).attr('value'));
            });
            $("input[name='selectAll']").prop("checked", self.ids.length === selector.length);
        };
        self.selectAll = function () {
            self.ids = [];
            var allSelect = $("input[name='selectAll']").prop("checked");
            $("input[name='topicId']").each(function (index, element) {
                $(element).prop('checked', allSelect);
                allSelect && self.ids.push($(element).attr('value'));
            });
        };

        self.id = $routeParams.boardId;
        var Topic = $resource('boards/:boardId/topic', {boardId: self.id});

        self.gotoPage = function (pageNo) {
            self.topics = Topic.get({boardId: id, pageNo: pageNo});
        };

        self.delete = function () {
            if (self.ids.length > 0)
                self.topics = $resource('boards/:boardId/topic/delete', {boardId: id}).save({boardId: id, topicIds: self.ids.join(','), pageNo: self.topics.currentPageNo});
        };

        self.digest = function () {
            if (self.ids.length > 0)
                self.topics = Topic.save({
                    boardId: id,
                    topicIds: self.ids.join(','),
                    pageNo: self.topics.currentPageNo
                });
        };
        self.topics = Topic.get();
    }]
});