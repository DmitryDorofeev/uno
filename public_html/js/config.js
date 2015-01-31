require.config({
    paths: {
        'jquery': 'lib/jquery/dist/jquery',
        'underscore': 'lib/underscore/underscore',
        'backbone': 'lib/backbone/backbone'
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
