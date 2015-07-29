var CalendarView = Backbone.View.extend({
    el: '#calendar',
    initialize: function () {
        this.render();
    },

    render: function () {
        this.$el.fullCalendar({
            theme: true
            , lang: 'ru'
            , aspectRatio: 1.65
            , header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            }
            , weekends: true
            , fixedWeekCount: false
            , firstDay: 1
            , editable: true
            , eventLimit: true
            , droppable: true
            , drop: function () {
            }
            , eventSources: [
                {
                    url: 'bug',
                    type: 'get',
                    data: function () {
                        return {
                            userIds: app.Inited.userView.getSelected()
                            , departmentIds: app.Inited.departmentView.getSelected()
                            , productIds: app.Inited.productView.getSelected()
                        }
                    },
                    error: function () {
                        alert('there was an error while fetching events!');
                    }
                }
            ]
            , eventRender: function (event, element) {
                element.find('.fc-title').append(
                    event.product.department.name + ' ' +
                    event.product.name + ' ' +
                    event.bttBugNo + ' - ' +
                    event.description + '<br/>' +
                    'Responsible user: ' + event.login);
            }

            , nextDayThreshold: "23:59:59"
        });

        return this;
    }

    , getCurrentViewDates: function () {
        var view = this.$el.fullCalendar('getView');
        return {
            'start': view.intervalStart.format()
            , 'end': view.intervalEnd.format()
        };
    }
    , reFetchEvents: function () {
        this.$el.fullCalendar('refetchEvents');
    }
});

var DepartmentView = Backbone.View.extend({
    el: '#departments'
    , isChosenReady: false
    , template: 'options'
    , initialize: function (items/*, initProductsCallBack, initUsersCallBack, initCalendarCallBack*/) {
        var jsonItems = items.toJSON();
        var selected = StorageManager.get(storages.departments);
        _.each(selected, function (item) {
            var findWhere = _.findWhere(jsonItems, {id: parseInt(item.selected)});
            if (findWhere) {
                findWhere.selected = true;
            }
        });
        this.items = {'items': jsonItems};
        this.render();
    }
    , render: function () {
        var self = this;

        TemplateManager.get(this.template, function (template) {
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            if (!self.isChosenReady) {
                self.$el.show();
                self.$el.chosen({max_selected_options: 8});
                self.$el.on('change', self.store);
                self.isChosenReady = true;
                initProductsCallBack();
            } else {
                self.$el.trigger("chosen:updated")
            }
        });

        return this;
    }
    , store: function (event, operation) {
        var selected = _.has(operation, "selected");
        var deselected = _.has(operation, "deselected");
        var departments = StorageManager.get(storages.departments);
        if (selected) {
            if (!_.findWhere(departments, operation)) {
                departments.push(operation);
                StorageManager.set(storages.departments, departments);
            }
        } else if (deselected) {
            departments = _.without(departments, _.findWhere(departments, {selected: operation.deselected}));
            StorageManager.set(storages.departments, departments);
        } else {
            throw Error('illegal chosen operation');
        }

        app.Inited.products.initialize();
        app.Inited.calendar.reFetchEvents();
    }
    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val().toString() : '';
    }
});

var ProductsView = Backbone.View.extend({
    el: '#products'
    , template: 'options'
    , isChosenReady: false
    , initialize: function (items) {
        var jsonItems = items.toJSON();
        var selected = StorageManager.get(storages.products);
        _.each(selected, function (item) {
            var findWhere = _.findWhere(jsonItems, {id: parseInt(item.selected)});
            if (findWhere) {
                findWhere.selected = true;
            }
        });
        this.items = {'items': jsonItems};
        this.render();
    }
    , render: function () {
        var self = this;
        TemplateManager.get(this.template, function (template) {
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            if (!self.isChosenReady) {
                self.$el.show();
                self.$el.on('change', self.store);
                self.$el.chosen({max_selected_options: 8});
                self.isChosenReady = true;
                initUsersCallBack();
            } else {
                self.$el.trigger("chosen:updated")
            }
        });

        return this;
    }
    , store: function (event, operation) {
        var selected = _.has(operation, "selected");
        var deselected = _.has(operation, "deselected");
        var products = StorageManager.get(storages.products);
        if (selected) {
            if (!_.findWhere(products, operation)) {
                products.push(operation);
                StorageManager.set(storages.products, products);
            }
        } else if (deselected) {
            products = _.without(products, _.findWhere(products, {selected: operation.deselected}));
            StorageManager.set(storages.products, products);
        } else {
            throw Error('illegal chosen operation');
        }
        app.Inited.calendar.reFetchEvents();
    }
    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val().toString() : '';
    }
});


var UsersView = Backbone.View.extend({
    el: '#users'
    , template: 'users-options'
    , isChosenReady: false
    , initialize: function (items) {
        var jsonItems = items.toJSON();
        var selected = StorageManager.get(storages.users);
        _.each(selected, function (item) {
            var findWhere = _.findWhere(jsonItems, {id: parseInt(item.selected)});
            if (findWhere) {
                findWhere.selected = true;
            }
        });
        this.items = {'items': jsonItems};
        this.render();
    }
    , render: function () {
        var self = this;
        TemplateManager.get(this.template, function (template) {
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            if (!self.isChosenReady) {
                self.$el.show();
                self.isChosenReady = true;
                self.$el.on('change', self.store);
                self.$el.chosen({max_selected_options: 8});
                initCalendarCallBack();
            } else {
                self.$el.trigger("chosen:updated")
            }
        });

        return this;
    }
    , store: function (event, operation) {
        var selected = _.has(operation, "selected");
        var deselected = _.has(operation, "deselected");
        var users = StorageManager.get(storages.users);
        if (selected) {
            if (!_.findWhere(users, operation)) {
                users.push(operation);
                StorageManager.set(storages.users, users);
            }
        } else if (deselected) {
            users = _.without(users, _.findWhere(users, {selected: operation.deselected}));
            StorageManager.set(storages.users, users);
        } else {
            throw Error('illegal chosen operation');
        }
    }
    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val().toString() : '';
    }
});


