require.config({
    baseUrl: 'js',
    paths: {
        'jquery': 'lib/jquery',
        'underscore': 'lib/underscore',
        'backbone': 'lib/backbone',
        'qunit': 'lib/qunit',
        'mockjax': 'lib/jquery.mockjax',
        'mock-socket': 'lib/mock-socket'
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'qunit': {
           exports: 'QUnit',
           init: function() {
               QUnit.config.autoload = false;
               QUnit.config.autostart = false;
           }
        },
        'mockjax': {
            deps: ['jquery']
        },
        'mock-socket': {
            exports: 'mock-socket'
        }
    }
});

require(['tests/main']);
