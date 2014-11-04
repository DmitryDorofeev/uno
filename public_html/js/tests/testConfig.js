require.config({
    baseUrl: 'js',
    paths: {
        'jquery': 'lib/jquery',
        'underscore': 'lib/underscore',
        'backbone': 'lib/backbone',
        'qunit': 'lib/qunit',
        'mockjax': 'lib/jquery.mockjax'
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
        } 
    }
});

require(['tests/main']);
