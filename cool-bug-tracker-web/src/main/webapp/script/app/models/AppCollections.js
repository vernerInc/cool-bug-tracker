var DepartmentsCollection = Backbone.Collection.extend({
    model: DepartmentModel
    , initialize: function () {
        return this;
    }
});

var ProductsCollection = Backbone.Collection.extend({
    model: ProductModel
    , url: function () {
        var url = 'products';
        var selectedDepartments = StorageManager.get(storages.departments);
        if (selectedDepartments) {
            var depsIds = [];
            _.each(selectedDepartments, function (item) {
                depsIds.push(item.selected);
            });
            return url + '/' + depsIds
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

var UsersCollection = Backbone.Collection.extend({
    model: UserModel
    , initialize: function () {
        return this;
    }
});

var BadBugCollection = Backbone.Collection.extend({
    model: BadBugModel
    , initialize: function (bugNo) {
        this.url = 'ext/bugs/findBugByBugNo/' + bugNo;
        return this;
    }
});