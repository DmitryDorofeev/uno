define(function (require) {
    var pixi = require('pixi');
    var Tink = require('tink');

    pixi.utils._saidHello = true;

    function Canvas(el) {
        this.texture = pixi.Texture.fromImage('/images/cards.png');
        this.cards = new pixi.Container();

        this.stage = new pixi.Container();
        this.stage.width = $(window).width();
        this.stage.height = $(window).height();

        this.palyers = {};

        this.renderer = pixi.autoDetectRenderer($(window).width(), $(window).height(), {
            view: el,
            transparent: true,
            noWebGL: true
        });

        this.stage.addChild(this.cards);

        this.tink = new Tink(pixi, this.renderer.view);

        this.render();
    }

    Canvas.prototype = {

        render: function () {
            requestAnimationFrame(this.render.bind(this));
            this.tink.update();
            this.renderer.render(this.stage);
        },

        addCard: function (card) {
            var cardSprite = new pixi.Sprite(this.texture);
            var mask = (new pixi.Graphics())
                .beginFill()
                .drawRect(card.x, card.y, card.width, card.height)
                .endFill();

            cardSprite.position.x = -card.x + card.pageX;
            cardSprite.position.y = -card.y + card.pageY;
            mask.interactive = true;
            mask.buttonMode = true;
            mask.defaultCursor = 'pointer';

            mask.click = function () {
                console.log('sas');
            };

            cardSprite.mask = mask;

            cardSprite.addChild(mask);

            this.cards.addChild(cardSprite);
            var centerPos = ($(window).width() - (this.cards.children.length * 120)) / 2;
            this.cards.position = new pixi.Point(centerPos, $(window).height() - 200);
        },

        removeCard: function () {

        }

    };

    return Canvas;
});
