define(['backbone'], function (Backbone) {
    var UserModel = Backbone.Model.extend({
        initialize: function () {
            $.get('/api/v1/auth/profile').done(_.bind(this._fetch, this));
        },
        isLogined: function () {
            return this.has('isLogined');
        },
        login: function () {
            //debugger;
            $.ajax({
                type: 'POST',
                url: '/api/v1/auth/signin',
                data: $.extend(this.toJSON(), {extra: 'joystick'}),
                dataType: 'json'
            }).done(_.bind(function (data) {
                debugger;
                if (data.status == 200) {
                    this.set('isLogined', true);
                    this.trigger('logined');
                }
                else {
                    this.trigger('error', data.message);
                }
            }, this)).fail(_.bind(function () {
                this.trigger('error');
            }, this));
        },
        _fetch: function (data) {
            if (data.status == 200) {
                this.set('isLogined', true);
            }
        }
    });

    return new UserModel();
});