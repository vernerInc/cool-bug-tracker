var TemplateManager = {
    templates: {},

    get: function (id, callback) {
        var template = this.templates[id];
        if (template) {
            callback(template.html());
        } else {
            var that = this;
            $.get("script/app/templates/tpl-" + id + ".html").then(function (template) {
                var $tmpl = $(template);
                that.templates[id] = $tmpl;
                callback($tmpl.html());
            });

        }

    }

};

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
        });

        return this;
    }
});

var FilterDepartmentView = Backbone.View.extend({
    el: '#departments'
    , template: 'options'
    , initialize: function (items) {
        var jsonItems = items.toJSON();
        var selected = app.LocalStorage.get(Storages.departments);
        _.each(selected, function(item) {
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
            self.$el.append(self.view_template(self.items));
            self.$el.show();
            self.$el.chosen();
            self.$el.on('change', self.store);

        });

        return this;
    }
    , store: function (event, operation) {
        var selected = _.has(operation, "selected");
        var deselected = _.has(operation, "deselected");
        var departments = app.LocalStorage.get(Storages.departments);
        if (selected) {
            if (!_.findWhere(departments, operation)) {
                departments.push(operation);
                app.LocalStorage.set(Storages.departments, departments);
            }
        } else if (deselected) {
            if (!_.findWhere(app.LocalStorage.get("departments"), operation)) {
                departments = _.without(departments, _.findWhere(departments, {selected: operation.deselected}));
                app.LocalStorage.set(Storages.departments, departments);
            }
        } else {
            throw Error('illegal chosen operation');
        }
    }
});

var LeftPanelActionsView = Backbone.View.extend({
    el: '#left-panel-actions'
    , initialize: function () {
        this.render();
    }
    , render: function () {
        return this;

    }
});


