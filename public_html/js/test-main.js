var allTestFiles = [];
var TEST_REGEXP = /(spec|test)\.js$/i;

var pathToModule = function(path) {
  return path.replace(/^\/base\//, '').replace(/\.js$/, '');
};

Object.keys(window.__karma__.files).forEach(function(file) {
  if (TEST_REGEXP.test(file)) {
    allTestFiles.push(file);
  }
});

require.config({
  baseUrl: '/base',
  deps: allTestFiles,
  callback: window.__karma__.start,
  paths: {
    'jquery': './lib/jquery/dist/jquery',
    'underscore': './lib/underscore/underscore',
    'backbone': './lib/backbone/backbone',
    // 'mockjax': 'lib/jquery.mockjax',
    // 'mock-socket': 'lib/mock-socket'
  },
  shim: {
    'backbone': {
      deps: ['underscore', 'jquery'],
      exports: 'Backbone'
    },
    'underscore': {
      exports: '_'
    },
    // 'mockjax': {
    //   deps: ['jquery']
    // },
    // 'mock-socket': {
    //   exports: 'mock-socket'
    // }
  }
});
