(function() {
    'use strict';

    angular
        .module('beakerApp')
        .controller('InjuryDeleteController',InjuryDeleteController);

    InjuryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Injury'];

    function InjuryDeleteController($uibModalInstance, entity, Injury) {
        var vm = this;

        vm.injury = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Injury.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
