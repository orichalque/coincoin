
var coincoinApp = angular.module('coincoinApp', []);


coincoinApp.controller('itemController', function itemController($scope) {
    $scope.item =
        {
            nom: 'Canard vivant 1',
            description: 'un bon gros canard'
        };
});