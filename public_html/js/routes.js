define([
  'jquery',
  'backbone',
  'views/home',
  'views/game',
  'views/login',
  'views/signup',
  'views/profile',
  'views/scoreboard',
  'views/app',
  'models/user'
], function($, Backbone, homeView, gameView, loginView, signupView, profileView, scoreboardView, manager, userModel) {
  
  manager.subscribe([homeView, gameView, loginView, signupView, profileView, scoreboardView]);
  
  var Router = Backbone.Router.extend({
    initialize: function () {
        this.listenTo(userModel, 'login:ok', this.toGame);
        this.listenTo(userModel, 'signup:ok', this.toLogin);
        this.listenTo(userModel, 'login:bad', this.toLogin);
        this.listenTo(userModel, 'logout', this.toMain);
    },
    routes: {
      '': 'index',
      'game': 'game',
      'login': 'login',
      'signup': 'signup',
      'profile': 'profile',
      'scoreboard': 'scoreboard',
      '*other': 'default'
    },
    index: function() {
      homeView.show();
    },
    game: function() {
      gameView.show();
    },
    login: function() {
      loginView.show();
    },
    signup: function() {
      signupView.show();
    },
    profile: function() {
      profileView.show();
    },
    scoreboard: function() {
      scoreboardView.show();
    },
    default: function () {
      alert('404'); // TODO: change to 404 View
    },
    toGame: function () {
      this.navigate('game', {trigger: true});
    },
    toLogin: function () {
      this.navigate('login', {trigger: true});
    },
    toMain: function () {
      this.navigate('', {trigger: true});
    }
  });

  return new Router();
});
