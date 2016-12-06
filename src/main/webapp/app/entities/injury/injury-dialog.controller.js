(function() {
    'use strict';

    angular
        .module('beakerApp')
        .controller('InjuryDialogController', InjuryDialogController);

    InjuryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Injury'];

    function InjuryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Injury) {
        var vm = this;

        vm.injury = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.injury.id !== null) {
                Injury.update(vm.injury, onSaveSuccess, onSaveError);
            } else {
                Injury.save(vm.injury, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('beakerApp:injuryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.inflicted = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
