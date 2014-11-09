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
  
    app.register({
        'home': HomeView,
        'game': GameView, 
        'login': LoginView, 
        'signup': SignupView, 
        'profile': ProfileView, 
        'scoreboard': ScoreboardView
    });
    app.render();

    var Router = Backbone.Router.extend({
        initialize: function () {
            this.listenTo(userModel, 'login:ok', this.afterLogin);
            this.listenTo(userModel, 'signup:ok', this.afterSignup);
            this.listenTo(userModel, 'login:no', this.afterSignup);
            this.listenTo(userModel, 'logout', this.afterLogout);
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
            app.getView('home').show();
        },
        game: function () {
            app.getView('game').show();
        },
        login: function () {
            app.getView('login').show();
        },
        signup: function () {
            app.getView('signup').show();
        },
        profile: function () {
            app.getView('profile').show();
        },
        scoreboard: function () {
            app.getView('scoreboard').show();
        },
        defaultRoute: function () {
            alert('404'); // TODO: change to 404 View
        },
        afterLogin: function () {
            this.navigate('/', {trigger: true});
        },
        afterSignup: function () {
            this.navigate('login', {trigger: true});
        },
        afterLogout: function () {
            this.navigate('/', {trigger: true});
        }
    });

    return new Router();
});
