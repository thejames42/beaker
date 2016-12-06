(function() {
    'use strict';
    angular
        .module('beakerApp')
        .factory('Injury', Injury);

    Injury.$inject = ['$resource', 'DateUtils'];

    function Injury ($resource, DateUtils) {
        var resourceUrl =  'api/injuries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.inflicted = DateUtils.convertLocalDateFromServer(data.inflicted);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.inflicted = DateUtils.convertLocalDateToServer(copy.inflicted);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.inflicted = DateUtils.convertLocalDateToServer(copy.inflicted);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
