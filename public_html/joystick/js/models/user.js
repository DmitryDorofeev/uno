define(['backbone'], function (Backbone) {
    var UserModel = Backbone.Model.extend({
        isLogined: function () {
            return this.has('isLogined');
        },
        login: function () {
            //debugger;
            $.ajax({
                type: 'POST',
                url: '/api/v1/auth/signin',
                data: $.extend(this.toJSON(), {extra: 'joystick'})
            }).done(_.bind(function (data) {
                if (data.status == 200) {
                    this.trigger('logined');
                }
                else {
                    this.trigger('error', data.message);
                }
            }, this)).fail(_.bind(function () {
                this.trigger('error');
            }, this));
        }
    });

    return new UserModel();
});