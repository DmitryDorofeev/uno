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
], function ($, Backbone, HomeView, GameView, LoginView, SignupView, ProfileView, ScoreboardView, app, userModel) {
  
/*
    app.register({
        'game': gameView, 
        'login':loginView, 
        'signup': signupView, 
        'profile': profileView, 
        'scoreboard': scoreboardView
    });
*/
    app.render();

    var Router = Backbone.Router.extend({
        initialize: function () {
            this.listenTo(userModel, 'login:ok', this.toGame);
            this.listenTo(userModel, 'signup:ok', this.toLogin);
            this.listenTo(userModel, 'login:no', this.toLogin);
            this.listenTo(userModel, 'logout', this.toMain);
        },
        routes: {
            '': 'index',
            'game': 'game',
            'login': 'login',
            'signup': 'signup',
            'profile': 'profile',
            'scoreboard': 'scoreboard',
            '*other': 'defaultRoute'
        },
        index: function () {
            app.getView('home', HomeView).show();
        },
        game: function () {
            app.getView('game', GameView).show();
        },
        login: function () {
            app.getView('login', LoginView).show();
        },
        signup: function () {
            app.getView('signup', SignupView).show();
        },
        profile: function () {
            app.getView('profile', ProfileView).show();
        },
        scoreboard: function () {
            app.getView('scoreboard', ScoreboardView).show();
        },
        defaultRoute: function () {
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
