require.config({
    paths: {
        'jquery': 'lib/jquery',
        'underscore': 'lib/underscore',
        'backbone': 'lib/backbone',
        'phaser': 'lib/phaser.min'
    },
    shim: {
        // 'backbone': {
        //     deps: ['underscore', 'jquery'],
        //     exports: 'Backbone'
        // },
        // 'underscore': {
        //     exports: '_'
        // },
        // 'phaser': {
        //     exports: 'Phaser'
        // }
    }
});

require(['main']);
