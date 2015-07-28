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
        , startDate: null
        , endDate: null
    }
    , idAttribute: "id"
    , initialize: function () {
        console.log('bug has been initialized');
        this.on("invalid", function (model, error) {
            console.log("Houston, we have a problem: " + error)
        });
    }
    , constructor: function (attributes, options) {
        console.log('Bug\'s constructor had been called');
        Backbone.Model.apply(this, arguments);
    }
    , validate: function (attr) {
        if (!attr.bttBugNo) {
            return "Invalid bttBugNo supplied."
        }
    }
    , urlRoot: 'bug'
});

var User = Backbone.Model.extend({
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
        console.log('bug has been initialized');
        this.on("invalid", function (model, error) {
            console.log("Houston, we have a problem: " + error)
        });
    }
    , constructor: function (attributes, options) {
        console.log('Bug\'s constructor had been called');
        Backbone.Model.apply(this, arguments);
    }
    , validate: function (attr) {
        if (!attr.bttBugNo) {
            return "Invalid bttBugNo supplied."
        }
    }
    , urlRoot: 'ext/user'
});
