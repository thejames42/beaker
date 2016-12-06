(function() {
    'use strict';

    angular
        .module('beakerApp')
        .controller('InjuryDetailController', InjuryDetailController);

    InjuryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Injury'];

    function InjuryDetailController($scope, $rootScope, $stateParams, previousState, entity, Injury) {
        var vm = this;

        vm.injury = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('beakerApp:injuryUpdate', function(event, result) {
            vm.injury = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
