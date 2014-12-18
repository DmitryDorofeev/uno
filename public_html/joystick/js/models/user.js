define(['backbone'], function (Backbone) {
    var dfd = new $.Deferred();

    var UserModel = Backbone.Model.extend({
        initialize: function () {
            _.bindAll(this, '_fetch');
            $.get('/api/v1/auth/profile?extra=joystick').done(this._fetch);
        },
        isLogined: function () {
            return dfd.promise();
        },
        login: function () {
            $.ajax({
                type: 'POST',
                url: '/api/v1/auth/signin',
                data: $.extend(this.toJSON(), {extra: 'joystick'}),
                dataType: 'json'
            }).done(_.bind(function (data) {
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
            data = JSON.parse(data);
            if (data.status === 200) {
                this.set('isLogined', true);
                dfd.resolve();
            }
            else {
                this.unset('isLogined');
                dfd.reject();
            }
        }
    });

    return new UserModel();
});