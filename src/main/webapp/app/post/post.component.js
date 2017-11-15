'use strict';
angular.module('post').component('post', {
    templateUrl: 'post/post.template.html',
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
            $("input[name='selectAll']").prop("checked", self.ids.length === selector.length || !1);
        };
        self.selectAll = function () {
            self.ids = [];
            var allSelect = $("input[name='selectAll']").prop("checked");
            $("input[name='topicId']").each(function (index, element) {
                $(element).prop('checked', allSelect);
                allSelect && self.ids.push($(element).attr('value'));
            });
        };

        var id = $routeParams.boardId;
        var Post = $resource('boards/:boardId/post', {boardId: id});

        self.gotoPage = function (pageNo) {
            self.posts = Post.get({boardId: id, pageNo: pageNo});
        }

        self.delete = function () {
            if (self.ids.length > 0)
                self.posts = Post.delete({boardId: id, topicIds: self.ids.join(','), pageNo: self.posts.currentPageNo});
        }

        self.digest = function () {
            if (self.ids.length > 0)
                self.posts = Post.save({
                    boardId: id,
                    topicIds: self.ids.join(','),
                    pageNo: self.posts.currentPageNo
                });
        }
        self.posts = Post.get();
    }]
});