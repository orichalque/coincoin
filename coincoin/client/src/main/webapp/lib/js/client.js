
var coincoinApp = angular.module('coincoinApp', []);

coincoinApp.controller('itemController', function itemController($scope, $http) {

    $scope.keyapi ="AIzaSyChJwwtkZUbukQ8Mk-Q6uI6GigZ7t2-T5s"
    $scope.cx = "000337515704215858772:qphvdedtcsw"
    $scope.item =
    {
        "nom": "canard",
        "description": "un bon gros canard"
    };
    $scope.requestUrl = "https://www.googleapis.com/customsearch/v1?key="+$scope.keyapi+"&cx="+$scope.cx+"&q="+$scope.item.nom+"&searchType=image&num=1"
    $http({
        method : "GET",
        url : $scope.requestUrl
    }).then(function mySucces(response) {
        $scope.data = response.data;
        $scope.img =  $scope.data.items[0];
        console.log( $scope.data.items[0]);
    }, function myError(response) {
        $scope.data = response.statusText;
    });


});