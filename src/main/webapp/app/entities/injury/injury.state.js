(function() {
    'use strict';

    angular
        .module('beakerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('injury', {
            parent: 'entity',
            url: '/injury',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'beakerApp.injury.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/injury/injuries.html',
                    controller: 'InjuryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('injury');
                    $translatePartialLoader.addPart('classification');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('injury-detail', {
            parent: 'entity',
            url: '/injury/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'beakerApp.injury.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/injury/injury-detail.html',
                    controller: 'InjuryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('injury');
                    $translatePartialLoader.addPart('classification');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Injury', function($stateParams, Injury) {
                    return Injury.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'injury',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('injury-detail.edit', {
            parent: 'injury-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/injury/injury-dialog.html',
                    controller: 'InjuryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Injury', function(Injury) {
                            return Injury.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('injury.new', {
            parent: 'injury',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/injury/injury-dialog.html',
                    controller: 'InjuryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                classification: null,
                                severity: null,
                                location: null,
                                inflicted: null,
                                fatal: null,
                                source: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('injury', null, { reload: 'injury' });
                }, function() {
                    $state.go('injury');
                });
            }]
        })
        .state('injury.edit', {
            parent: 'injury',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/injury/injury-dialog.html',
                    controller: 'InjuryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Injury', function(Injury) {
                            return Injury.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('injury', null, { reload: 'injury' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('injury.delete', {
            parent: 'injury',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/injury/injury-delete-dialog.html',
                    controller: 'InjuryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Injury', function(Injury) {
                            return Injury.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('injury', null, { reload: 'injury' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
