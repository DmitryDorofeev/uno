require.config({
    paths: {
        'jquery': 'lib/jquery/dist/jquery',
        'underscore': 'lib/underscore/underscore',
        'backbone': 'lib/backbone/backbone',
        'createjs': 'lib/easeljs/lib/easeljs-NEXT.min',
        'pixi': 'lib/pixi.js/bin/pixi',
        'tink': 'lib/tink/bin/tink'
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'pixi': {
            exports: 'PIXI'
        },
        'tink': {
            exports: 'Tink'
        }
    }
});

require(['main']);
