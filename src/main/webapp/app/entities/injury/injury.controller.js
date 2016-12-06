(function() {
    'use strict';

    angular
        .module('beakerApp')
        .controller('InjuryController', InjuryController);

    InjuryController.$inject = ['$scope', '$state', 'Injury'];

    function InjuryController ($scope, $state, Injury) {
        var vm = this;

        vm.injuries = [];

        loadAll();

        function loadAll() {
            Injury.query(function(result) {
                vm.injuries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
