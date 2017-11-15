'use strict';

angular.module('navigator').component('navigator', {
        templateUrl: 'navigator/navigator.template.html',
        bindings: {
            currentPage: '<',
            hasNext: '<',
            hasPre: '<',
            totalPage: '<',
            totalCount: '<',
            gotoPage: '&'
        },
        controller: [function () {
            var self = this;

            self.gotoFirst = function () {
                self.gotoPage({pageNo: 1});
            }
            self.gotoPre = function () {
                self.gotoPage({pageNo: self.currentPage - 1});
            }
            self.gotoNext = function () {
                self.gotoPage({pageNo: self.currentPage + 1});
            }
            self.gotoLast = function () {
                self.gotoPage({pageNo: self.totalPage});
            }
        }
        ]

    }
)