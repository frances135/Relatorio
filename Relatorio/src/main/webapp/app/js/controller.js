var contextoRoot = "/Relatorio/";
var app = angular.module("app", []);

app.controller("controller", function($scope, $http) {
    $scope.ipCliente = "";
    $scope.dateDe = "";
    $scope.dateAte = "";
    $scope.httpMethod = "";
    $scope.resource = "";
	$scope.status = "";
	$scope.bytes = "";
	$scope.principalRequest = false;
	$scope.userAgent = "";
    
    $scope.logs = new Object();
    
    $scope.getLogs = function () {
    	
    	console.log($scope.ipCliente);
    	console.log(new Date($scope.dateDe).getTime());
    	console.log(new Date($scope.dateAte).getTime());
    	console.log($scope.httpMethod);
    	console.log($scope.resource);
    	console.log($scope.status);
    	console.log($scope.bytes);
    	console.log($scope.principalRequest);
    	console.log($scope.userAgent);
    	
    	$("#loading").show();
    	
    	$http({
            method : "GET",
            url : contextoRoot + "rest/relatorios/get?&ipCliente=" + $scope.ipCliente + 
			    	"&dateDe=" + (new Date($scope.dateDe).getTime()) + 
			    	"&dateAte=" + (new Date($scope.dateAte).getTime()) + 
			    	"&httpMethod=" + $scope.httpMethod + 
			    	"&resource=" + encodeURIComponent($scope.resource) + 
			    	"&status=" + $scope.status + 
			    	"&bytes=" + $scope.bytes + 
			    	"&principalRequest=" + $scope.principalRequest + 
			    	"&userAgent=" + encodeURIComponent($scope.userAgent)
        })
        .then(function successCallback(response) {
        	$scope.logs = response.data;
        	$("#loading").hide();
        	
        }, function errorCallback(response) {
        	alert(response);
        	$("#loading").hide();
        });
    };
    
    $scope.getLogs();
});