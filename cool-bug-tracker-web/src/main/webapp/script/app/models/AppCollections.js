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
        new app.Views.DepartmentView(collection);
    }

    , fetchError: function (collection, response) {
        alert(response);
    }
});

var Products = Backbone.Collection.extend({
    model: ProductModel
    , url: function () {
        var url = 'bug/products';
        var selectedDepartments = StorageManager.get(storages.departments);
        if (selectedDepartments) {
            var depsIds = [];
            _.each(selectedDepartments, function (item) {
                depsIds.push(item.selected);
            });
            return 'bug/products/' + depsIds
        } else {
            return url;
        }
    }
    , initialize: function () {
        this.fetch({
            success: this.fetchSuccess
            , error: this.fetchError
        });

        return this;
    }

    , fetchSuccess: function (collection, response) {
        if (app.Inited.productView) {
            app.Inited.productView.initialize(collection)
        } else {
            app.Inited.productView = new app.Views.ProductsView(collection);
        }
    }

    , fetchError: function (collection, response) {
        alert(response);
    }
});

var Users = Backbone.Collection.extend({
    model: User
    , url: 'ext/users'
    , initialize: function () {
        this.fetch({
            success: this.fetchSuccess
            , error: this.fetchError
        });

        return this;
    }

    , fetchSuccess: function (collection, response) {
        new app.Views.UsersView(collection);
    }

    , fetchError: function (collection, response) {
        alert(response);
    }
});