require.config({
    paths: {
        'jquery': '../../js/lib/jquery',
        'underscore': '../../js/lib/underscore',
        'backbone': '../../js/lib/backbone'
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        }
    }
});

require(['main']);
