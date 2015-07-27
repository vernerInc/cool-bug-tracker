var Departments = Backbone.Collection.extend({
    model: DepartmentModel
    , url: 'bug/departments'
    , initialize: function () {
        this.fetch({
             success: this.fetchSuccess
            , error: this.fetchError
        });

        return this;
    }

    , fetchSuccess: function (collection, response) {
        new app.Views.FilterDepartmentView(collection);
    }

    , fetchError: function (collection, response) {
        alert(response);
    }
});