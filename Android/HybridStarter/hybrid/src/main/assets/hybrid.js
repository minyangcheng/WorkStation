(function () {

  var host = window.location.host;

  var Dispatcher = {
    callbacks: {},

    send: function (envelope, complete) {
      this.dispatchMessage("event", envelope, complete);
    },

    sendCallback: function (messageId) {
      var envelope = Hybrid.createEnvelope(messageId);
      this.dispatchMessage("callback", envelope, function () {
      });
    },

    triggerCallback: function (id, payload) {
      var dispatcher = this;
      setTimeout(function () {
        dispatcher.callbacks[id](payload);
      }, 0);
    },

    dispatchMessage: function (type, envelope, complete) {
      var dispatcher = this;
      this.callbacks[envelope.id] = function (payload) {
        complete(payload);
        delete dispatcher.callbacks[envelope.id];
      };
      window.location.href = "hybrid://" + type + "/" + envelope.id + "?" + encodeURIComponent(JSON.stringify(envelope));
    }
  };


  var Hybrid = {
    listeners: {},

    dispatcher: null,

    messageCount: 0,

    on: function (type, fn) {
      if (!this.listeners.hasOwnProperty(type) || !this.listeners[type] instanceof Array) {
        this.listeners[type] = [];
      }
      this.listeners[type].push(fn);
    },

    off: function (type) {
      if (!this.listeners.hasOwnProperty(type) || !this.listeners[type] instanceof Array) {
        this.listeners[type] = [];
      }
      this.listeners[type] = [];
    },

    send: function (type, payload, complete) {
      if (payload instanceof Function) {
        complete = payload;
        payload = null;
      }
      payload = payload || {};
      complete = complete || function () {};
      var envelope = this.createEnvelope(this.messageCount, type, payload);
      this.dispatcher.send(envelope, complete);
      this.messageCount += 1;
    },

    trigger: function (type, messageId, json) {
      var self = this;
      var listenerList = this.listeners[type] || [];
      var executedCount = 0;
      var complete = function () {
        executedCount += 1;
        if (executedCount >= listenerList.length) {
          self.dispatcher.sendCallback(messageId);
        }
      };

      for (var index = 0; index < listenerList.length; index++) {
        var listener = listenerList[index];
        if (listener.length <= 1) {
          listener(json);
          complete();
        } else {
          listener(json, complete);
        }
      }
    },

    triggerCallback: function (id, payload) {
      this.dispatcher.triggerCallback(id, payload);
    },

    createEnvelope: function (id, type, payload) {
      return {
        id: id,
        type: type,
        host: host,
        payload: payload
      };
    }
  };

  var nullDispatcher = {
    send: function () {
    },
    triggerCallback: function () {
    },
    sendCallback: function () {
    }
  };

  var i = 0,
    iOS = false,
    iDevice = ['iPad', 'iPhone', 'iPod'];

  for (; i < iDevice.length; i++) {
    if (navigator.platform.indexOf(iDevice[i]) >= 0) {
      iOS = true;
      break;
    }
  }
  var UIWebView = /(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/i.test(navigator.userAgent);
  var isAndroid = navigator.userAgent.toLowerCase().indexOf("android") > -1;
  if ((iOS && UIWebView) || isAndroid) {
    Hybrid.dispatcher = Dispatcher;
  } else {
    Hybrid.dispatcher = nullDispatcher;
  }

  window.Hybrid = Hybrid;
})();
