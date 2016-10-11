
/**
 * Angular app linked to <html> tag of index.html
 */
var coincoinApp = angular.module('coincoinApp', ['ngAnimate']);

/**
 * Angular controller linked to <body> tag of index.html
 */
coincoinApp.controller('itemController', function ($scope, $http, $interval, $location) {

    /**
     *
     * Google custom search request
     * Used to find the first occurence of an img search
     */
    $scope.keyapi ="AIzaSyChJwwtkZUbukQ8Mk-Q6uI6GigZ7t2-T5s"
    $scope.cx = "000337515704215858772:qphvdedtcsw"

    $scope.authentified = false;
    $scope.subscribed = false;

    $scope.name;
    $scope.mail;
    $scope.domaine = $location.protocol() + "://" + $location.host() + ":" + $location.port();

    $scope.item = "";

    /**
     * Control of the item
     */
    $scope.isItemEmpty = function() {
        return ( $scope.item == "" );
    }

    $scope.prixActuel = $scope.isItemEmpty()?0:$scope.item.prix;
    $scope.prixPropose = $scope.prixActuel + 1;

    /**
     * Used to change the price of the item
     */
    $scope.changerPrix = function() {
        if (($scope.prixPropose > $scope.prixActuel) && !$scope.isItemEmpty) {
            var parameters = {
                nom:$scope.item.nom,
                newPrice:$scope.prixPropose
            }
            $http({
                method: "POST",
                url: $scope.domaine+"/bid",
                params: parameters

            }).then(function () {
                $scope.prixActuel = $scope.prixPropose;
            }, function () {
            });
        }
    };

    /**
     * This function will be called every 2sec
     * Used to refresh the UI
     */
    $interval(updateInterface, 2000);

    function updateInterface() {
        if ($scope.authentified) {
            $http({
                method : "GET",
                url : $scope.domaine+"/update"
            }).then(function (response) {
                if ((response.data != "") && ($scope.isItemEmpty() || (response.data.nom != $scope.item.nom))){
                    $scope.item = response.data;
                    $scope.prixActuel = $scope.isItemEmpty()?0:$scope.item.prix;
                    $scope.prixPropose = $scope.prixActuel + 1;
                    $scope.requestUrl = $scope.isItemEmpty()?"":"https://www.googleapis.com/customsearch/v1?key="+$scope.keyapi+"&cx="+$scope.cx+"&q="+$scope.item.nom+"&searchType=image&num=1";
                    $http({
                        method: "GET",
                        url: $scope.requestUrl
                    }).then(function (response) {
                        $scope.data = response.data;
                        $scope.img = $scope.data.items[0];
                    }, function (response) {
                        $scope.data = response.statusText;
                    });
                }
                if ((response.data != "") && (response.data.nom == $scope.item.nom) && ($scope.item.prix != response.data.prix)){
                    $scope.item.prix = response.data.prix;
                    $scope.prixActuel = $scope.item.prix;
                    $scope.prixPropose = $scope.prixActuel + 1;
                }
            }, function () {
            });
        }
    }

    $scope.authentify = function() {
        $http({
            method : "POST",
            url : $scope.domaine+"/authentify",
            params : {"nom":$scope.name, "mail":$scope.mail}
        }).then(function () {
            $scope.authentified = true;
        }, function () {
        });
    }

    $scope.subscribe = function() {
        $http({
            method : "POST",
            url : $scope.domaine+"/subscribe",
            params : {"nom":$scope.name, "mail":$scope.mail}
        }).then(function () {
        }, function () {
        });
        $scope.subscribed = true;
    }

});