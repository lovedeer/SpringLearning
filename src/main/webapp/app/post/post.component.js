'use strict';
angular.module('post').component('post', {
    templateUrl: 'post/post.template.html',
    transclude: true,
    controller: ['$resource', '$routeParams', function ($resource, $routeParams) {
        var self = this;
        self.boardId = $routeParams.boardId;
        self.topicId = $routeParams.topicId;
        var Post = $resource('board/:boardId/topic/:topicId/post', {boardId: self.boardId, topicId: self.topicId});
        self.posts = Post.get();
        self.reply = function () {
            if (!this.postTitle || !this.postText)
                return false;
            self.posts = $resource('board/:boardId/topic/:topicId/post/reply', {
                boardId: self.boardId,
                topicId: self.topicId
            }).save({
                postTitle: this.postTitle,
                postText: this.postText,
                pageNo: self.posts.currentPageNo
            }, function () {
                self.postTitle = "";
                self.postText = "   ";
                self.form.$submitted = false;
            });
        }
        self.gotoPage = function (pageNo) {
            self.posts = Post.get({boardId: self.boardId, topicId: self.topicId, pageNo: pageNo});
        }
    }]
});