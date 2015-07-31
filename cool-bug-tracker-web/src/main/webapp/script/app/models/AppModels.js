var DepartmentModel = Backbone.Model.extend({
    defaults: {
        id: null
        , name: ''
        , description: ''
    }
    , url: 'bug/departments'
});


var ProductModel = Backbone.Model.extend({
    defaults: {
        id: null
        , name: ''
        , description: ''
        , department: {}
        , bttProductId: null
        , bttSubSystemId: null
    }
});

var BugModel = Backbone.Model.extend({
    defaults: {
        id: null
        , bttBugId: null
        , bttBugNo: null
        , product: {}
        , start: null
        , end: null
    }
    , idAttribute: "id"
    , initialize: function () {
        debugger;
        console.log('bug has been initialized');
        this.on("invalid", function (model, error) {
        });
    }
    , constructor: function (attributes, options) {
        debugger;
        Backbone.Model.apply(this, arguments);
    }
    , validate: function (attr) {
    }
    , urlRoot: 'bug'
});

var UserModel = Backbone.Model.extend({
    defaults: {
        id: null
        , login: null
        , firstName: null
        , lastName: {}
        , middleName: null
        , boss: null
        , creatingTime: null
        , department: null
        , showAll: false
        , isDeleted: false
    }
    , idAttribute: "id"
    , initialize: function () {
    }
    , validate: function (attr) {
    }
    , urlRoot: 'ext/user'
});

var BadBugModel = Backbone.Model.extend({
    defaults: {
        id: null
        , bugNo: null
        , bugDate: null
        , productId: {}
        , bugTypeId: null
        , status: null
        , priority: null
        , title: null
        , description: null
        , version: {}
        , deadLine: null
        , system: {}
        , productVersion: {}
        , project: {}
        , parent: {}
    }
    , idAttribute: "id"
    , initialize: function () {
    }
    , validate: function (attr) {
    }
    , urlRoot: 'ext/bugs'
});
