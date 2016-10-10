
/**
 * Angular app linked to <html> tag of index.html
 */
var coincoinApp = angular.module('coincoinApp', []);

/**
 * Angular controller linked to <body> tag of index.html
 */
coincoinApp.controller('itemController', function itemController($scope, $http, $interval) {

    /**
     *
     * Google custom search request
     * Used to find the first occurence of an img search
     */
    $scope.keyapi =""//"AIzaSyChJwwtkZUbukQ8Mk-Q6uI6GigZ7t2-T5s"
    $scope.cx = "000337515704215858772:qphvdedtcsw"

    $scope.authentified = false;
    $scope.subscribed = false;

    $scope.name;
    $scope.mail;

    $scope.item =
    {
        "nom": "Magnifique canard",
        "description": "Un magnifique canard idéal pour commencer une collection de canards (vivants). Volaille en très bon état. aucune plume manquante ! cause : Décès du proprietaire.",
        "prix": 120
    };

    $scope.prixActuel = $scope.item.prix;
    $scope.prixPropose = $scope.prixActuel + 1;
    $scope.requestUrl = "https://www.googleapis.com/customsearch/v1?key="+$scope.keyapi+"&cx="+$scope.cx+"&q="+$scope.item.nom+"&searchType=image&num=1"
    $http({
        method : "GET",
        url : $scope.requestUrl
    }).then(function mySucces(response) {
        $scope.data = response.data;
        $scope.img =  $scope.data.items[0];
    }, function myError(response) {
        $scope.data = response.statusText;
    });

    /**
     * Used to change the price of the item
     */
    $scope.changerPrix = function() {
        if ($scope.prixPropose > $scope.prixActuel) {
            var parameters = {nom:$scope.item.nom,newPrice:$scope.prixPropose}
            $http({
                method: "POST",
                url: "/bid",
                params: parameters

            }).then(function mySucces(response) {
                $scope.prixActuel = $scope.prixPropose;
            }, function myError(response) {
                console.log(response.data);
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
                url : "/update"
            }).then(function mySucces(response) {
                $scope.data = response.data;
                console.log(response.data);
            }, function myError(response) {
                console.log(response.data);
            });
        }
    }

    $scope.authentify = function() {
        $http({
            method : "POST",
            url : "/authentify",
            params : {"nom":$scope.name, "mail":$scope.mail}
        }).then(function success(response) {
            $scope.authentified = true;
            console.log("successfully logged");
        }, function error(response) {
            console.log(response.data);
        });
    }

    $scope.subscribe = function() {
        $http({
            method : "POST",
            url : "/subscribe",
            params : {"nom":$scope.name, "mail":$scope.mail}
        }).then(function success(response) {
            $scope.subscribed = true;
            console.log("inscription sended to the server");
        }, function error(response) {
            console.log(response.data);
        });
    }

});