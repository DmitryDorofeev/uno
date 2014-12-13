(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
// Starting point for browserify and throws important objects into the window object
var protocol   = require('./protocol');
var mockserver = require('./mock-server');
var mocksocket = require('./mock-socket');
var subject    = require('./helpers/subject');

window.Subject = subject;
window.Protocol = protocol;
window.MockSocket = mocksocket;
window.WebSocketServer = mockserver;

},{"./helpers/subject":4,"./mock-server":7,"./mock-socket":8,"./protocol":9}],2:[function(require,module,exports){
/**
* This delay allows the thread to finish assigning its on* methods
* before invoking the delay callback. This is purely a timing hack.
* http://geekabyte.blogspot.com/2014/01/javascript-effect-of-setting-settimeout.html
*
* @param {callback: function} the callback which will be invoked after the timeout
* @parma {context: object} the context in which to invoke the function
*/
function delay(callback, context) {
  window.setTimeout(function(context) {
    callback.call(context);
  }, 4, context);
}

module.exports = delay;

},{}],3:[function(require,module,exports){
/*
* This is a mock websocket event message that is passed into the onopen,
* opmessage, etc functions.
*
* @param {name: string} The name of the event
* @param {data: *} The data to send.
* @param {origin: string} The url of the place where the event is originating.
*/
function socketEventMessage(name, data, origin) {
	var bubbles 				= false;
	var cancelable 			= false;
	var lastEventId 		= '';
	var source					= null;
	var ports 					= null;
	var targetPlacehold = null;

	try {
		var messageEvent = new MessageEvent(name);
		messageEvent.initMessageEvent(name, bubbles, cancelable, data, origin, lastEventId);

		Object.defineProperties(messageEvent, {
			target:  {
				get: function() { return targetPlacehold; },
				set: function(value) { targetPlacehold = value; }
			},
			srcElement: {
				get: function() { return this.target; }
			},
			currentTarget: {
				get: function() { return this.target; }
			}
		});
	}
	catch (e) {
		// We are unable to create a MessageEvent Object. This should only be happening in PhantomJS.
		var messageEvent = {
			bubbles: bubbles,
			cancelable: cancelable,
			data: data,
			origin: origin,
			lastEventId: lastEventId,
			source: source,
			ports: ports,
			defaultPrevented: false,
			returnValue: true,
			clipboardData: undefined
		};

		Object.defineProperties(messageEvent, {
			target:  {
				get: function() { return targetPlacehold; },
				set: function(value) { targetPlacehold = value; }
			},
			srcElement: {
				get: function() { return this.target; }
			},
			currentTarget: {
				get: function() { return this.target; }
			}
		});
	}

	return messageEvent;
}

module.exports = socketEventMessage;

},{}],4:[function(require,module,exports){
function Subject() {
  this.list = {};
}

Subject.prototype = {

  /**
  * Binds a callback to a namespace. If notify is called on a namespace all "observers" will be
  * fired with the context that is passed in.
  *
  * @param {namespace: string}
  * @param {namespace: function}
  * @param {namespace: object}
  */
  observe: function(namespace, callback, context) {

    // Make sure the arguments are of the correct type
    if( typeof namespace !== 'string' || typeof callback !== 'function' || (context && typeof context !== 'object')) {
      return false;
    }

    // If a namespace has not been created before then we need to "initialize" the namespace
    if(!this.list[namespace]) {
      this.list[namespace] = [];
    }

    this.list[namespace].push({callback: callback, context: context});
  },

  /**
  * TODO: Fix this
  */
  unobserve: function(namespace, obj) {
    for (var i = 0, len = this.list[namespace].length; i < len; i++) {
      if (this.list[namespace][i] === obj) {
        this.list[namespace].splice(i, 1);
        return true;
      }
    }

    return false;
  },

  /**
  * Remove all observers from a given namespace.
  *
  * @param {namespace: string} The namespace to clear.
  */
  clearAll: function(namespace) {

    if(typeof namespace !== 'string') {
      return false;
    }

    this.list[namespace] = [];
  },

  /**
  * Notify all callbacks that have been bound to the given namespace.
  *
  * @param {namespace: string} The namespace to notify observers on.
  */
  notify: function(namespace) {

    // This strips the namespace from the list of args as we dont want to pass that into the callback.
    var argumentsForCallback = Array.prototype.slice.call(arguments, 1);

    if(typeof namespace !== 'string' || !this.list[namespace]) {
      return false;
    }

    // Loop over all of the observers and fire the callback function with the context.
    for(var i = 0, len = this.list[namespace].length; i < len; i++) {
      this.list[namespace][i].callback.apply(this.list[namespace][i].context, argumentsForCallback);
    }
  }
};

module.exports = Subject;

},{}],5:[function(require,module,exports){
/**
* The native websocket object will transform urls without a pathname to have just a /.
* As an example: ws://localhost:8080 would actually be ws://localhost:8080/ but ws://example.com/foo would not
* change. This function does this transformation to stay inline with the native websocket implementation.
*
* @param {url: string} The url to transform.
*/
function urlTransform(url) {
  var a = document.createElement('a');
  a.href = url;

  // Note: that the a.pathname === '' is for phantomJS
  if((a.pathname === '/' || a.pathname === '') && url.slice(-1) !== '/') {
    url += '/';
  }

  return url;
}

module.exports = urlTransform;

},{}],6:[function(require,module,exports){
/**
* This defines four methods: onopen, onmessage, onerror, and onclose. This is done this way instead of
* just placing the methods on the prototype because we need to capture the callback when it is defined like so:
*
* mockSocket.onopen = function() { // this is what we need to store };
*
* The only way is to capture the callback via the custom setter below and then place them into the correct
* namespace so they get invoked at the right time.
*
* @param {websocket: object} The websocket object which we want to define these properties onto
*/
function webSocketProperties(websocket) {
  var eventMessageSource = function(callback) {
    return function(event) {
      event.target = websocket;
      callback.apply(websocket, arguments);
    }
  };

  Object.defineProperties(websocket, {
    onopen: {
      enumerable: true,
      get: function() { return websocket._onopen; },
      set: function(callback) {
        websocket._onopen = eventMessageSource(callback);
        websocket.protocol.subject.observe('clientOnOpen', websocket._onopen, websocket);
      }
    },
    onmessage: {
      enumerable: true,
      get: function() { return websocket._onmessage; },
      set: function(callback) {
        websocket._onmessage = eventMessageSource(callback);
        websocket.protocol.subject.observe('clientOnMessage', websocket._onmessage, websocket);
      }
    },
    onclose: {
      enumerable: true,
      get: function() { return websocket._onclose; },
      set: function(callback) {
        websocket._onclose = eventMessageSource(callback);
        websocket.protocol.subject.observe('clientHasLeft', websocket._onclose, websocket);
      }
    },
    onerror: {
      enumerable: true,
      get: function() { return websocket._onerror; },
      set: function(callback) {
        websocket._onerror = eventMessageSource(callback);
        websocket.protocol.subject.observe('clientOnError', websocket._onerror, websocket);
      }
    }
  });
};

module.exports = webSocketProperties;

},{}],7:[function(require,module,exports){
var Protocol         = require('./protocol');
var delay            = require('./helpers/delay');
var Subject          = require('./helpers/subject');
var urlTransform     = require('./helpers/url-transform');
var socketMessageEvent = require('./helpers/message-event');

function WebSocketServer(url) {
  var subject   = new Subject();
  var protocol  = new Protocol(subject);
  this.url      = urlTransform(url);

  // TODO: Is there a better way of doing this?
  if(window.hasOwnProperty('MockSocket')) {
    window.MockSocket.protocol = protocol;
    this.protocol = protocol;
    protocol.server = this;
  }
}

WebSocketServer.prototype = {
  protocol: null,

  /**
  * This is the main function for the mock server to subscribe to the on events.
  *
  * ie: mockServer.on('connection', function() { console.log('a mock client connected'); });
  *
  * @param {type: string}: The event key to subscribe to. Valid keys are: connection, message, and close.
  * @param {callback: function}: The callback which should be called when a certain event is fired.
  */
  on: function(type, callback) {
    var observerKey;

    if(typeof callback !== 'function') {
      return false;
    }

    switch(type) {
      case 'connection':
        observerKey = 'clientHasJoined';
        break;
      case 'message':
        observerKey = 'clientHasSentMessage';
        break;
      case 'close':
        observerKey = 'clientHasLeft';
        break;
    }

    // Make sure that the observerKey is valid before observing on it.
    if(typeof observerKey === 'string') {
      this.protocol.subject.observe(observerKey, callback, this);
    }
  },

  /**
  * This send function will notify all mock clients via their onmessage callbacks that the server
  * has a message for them.
  *
  * @param {data: *}: Any javascript object which will be crafted into a MessageObject.
  */
  send: function(data) {
    delay(function() {
      this.protocol.subject.notify('clientOnMessage', socketMessageEvent('message', data, this.url));
    }, this);
  },

  /**
  * Notifies all mock clients that the server is closing and their onclose callbacks should fire.
  */
  close: function() {
    delay(function() {
      this.protocol.closeConnection(this);
    }, this);
  }
}

module.exports = WebSocketServer;

},{"./helpers/delay":2,"./helpers/message-event":3,"./helpers/subject":4,"./helpers/url-transform":5,"./protocol":9}],8:[function(require,module,exports){
var delay               = require('./helpers/delay');
var urlTransform        = require('./helpers/url-transform');
var socketMessageEvent  = require('./helpers/message-event');
var webSocketProperties = require('./helpers/websocket-properties');

function MockSocket(url) {
  this.binaryType = 'blob';
  this.url        = urlTransform(url);
  this.readyState = MockSocket.CONNECTING;
  this.protocol   = MockSocket.protocol;

  webSocketProperties(this);

  delay(function() {
    // Let the protocol know that we are both ready to change our ready state and that
    // this client is connecting to the mock server.
    this.protocol.subject.observe('updateReadyState', this._updateReadyState, this);
    this.protocol.subject.notify('clientAttemptingToConnect');
  }, this);
}

MockSocket.CONNECTING = 0;
MockSocket.OPEN = 1;
MockSocket.CLOSING = 2;
MockSocket.LOADING = 3;
MockSocket.CLOSED = 4;

MockSocket.prototype = {

  /*
  * Holds the on*** callback functions. These are really just for the custom
  * getters that are defined in the helpers/websocket-properties. Accessing these properties is not advised.
  */
  _onopen: null,
  _onmessage: null,
  _onerror: null,
  _onclose: null,

  /*
  * This holds reference to the protocol object. The protocol object is how we can
  * communicate with the backend via the pub/sub model.
  *
  * The protocol a property called subject which we can use to observe or notifiy with.
  * this.protocol.subject.notify('foo') & this.protocol.subject.observe('foo', callback, context)
  */
  protocol: null,

  /**
  * This is a mock for the native send function found on the WebSocket object. It notifies the
  * protocol that it has sent a message.
  *
  * @param {data: *}: Any javascript object which will be crafted into a MessageObject.
  */
  send: function(data) {
    delay(function() {
      this.protocol.subject.notify('clientHasSentMessage', socketMessageEvent('message', data, this.url));
    }, this);
  },

  /**
  * This is a mock for the native close function found on the WebSocket object. It notifies the
  * protocol that it is closing the connection.
  */
  close: function() {
    delay(function() {
      this.protocol.closeConnection(this);
    }, this);
  },

  /**
  * This is a private method that can be used to change the readyState. This is used
  * like this: this.protocol.subject.observe('updateReadyState', this._updateReadyState, this);
  * so that the protocol and the server can change the readyState simply be notifing a namespace.
  *
  * @param {newReadyState: number}: The new ready state. Must be 0-4
  */
  _updateReadyState: function(newReadyState) {
    this.readyState = newReadyState;
  }
};

module.exports = MockSocket;

},{"./helpers/delay":2,"./helpers/message-event":3,"./helpers/url-transform":5,"./helpers/websocket-properties":6}],9:[function(require,module,exports){
var socketMessageEvent = require('./helpers/message-event');

function Protocol(subject) {
  this.subject = subject;
  this.subject.observe('clientAttemptingToConnect', this.clientAttemptingToConnect, this);
}

Protocol.prototype = {
  server: null,
  clientAttemptingToConnect: function() {
    // If the server is not ready and the client tries to connect this results in a the onerror method
    // being invoked.
    if(!this.server) {
      this.subject.notify('updateReadyState', MockSocket.CLOSED);
      this.subject.notify('clientOnError');
      return false;
    }

    this.subject.notify('updateReadyState', MockSocket.OPEN);
    this.subject.notify('clientHasJoined', this.server);
    this.subject.notify('clientOnOpen', socketMessageEvent('open', null, this.server.url));
  },

  closeConnection: function(initiator) {
    this.subject.notify('updateReadyState', MockSocket.CLOSED);
    this.subject.notify('clientHasLeft', socketMessageEvent('close', null, initiator.url));
  }
};

module.exports = Protocol;

},{"./helpers/message-event":3}]},{},[1]);
