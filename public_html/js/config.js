require.config({
    paths: {
        'jquery': 'lib/jquery/dist/jquery',
        'underscore': 'lib/underscore/underscore',
        'backbone': 'lib/backbone/backbone',
        'libcanvas': 'lib/libcanvas/libcanvas-full-compiled',
        'atom': 'lib/atom/atom-full-compiled'
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'libcanvas': {
            deps: ['atom'],
            exports: 'LibCanvas'
        }
    }
});

require(['main']);
