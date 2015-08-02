var LeftPanelActionsView = Backbone.View.extend({
    el: '#left-panel-actions'

    , initialize: function () {
        app.Inited.foundBugsView = new FoundBugsView();
        app.Inited.foundBugsDepartmentView = new FoundBugsDepartmentView(app.Inited.departments);

        this.initFormDate();
        this.initToDate();
        $("#to-calendar").button();

        this.render();
    }

    , initFormDate: function () {
        var self = this;
        $("#from").datepicker({
            changeMonth: true,
            numberOfMonths: 3,
            dateFormat: 'dd.mm.yy',
            firstDay: 1,
            onClose: function (selectedDate) {
                $("#to").datepicker("option", "minDate", selectedDate);
            }
        });
    }
    , initToDate: function () {
        var self = this;
        $("#to").datepicker({
            changeMonth: true,
            dateFormat: 'dd.mm.yy',
            numberOfMonths: 3,
            firstDay: 1,
            onClose: function (selectedDate) {
                $("#from").datepicker("option", "maxDate", selectedDate);
            }
        });
    }

    , events: {
        'keypress #searchBug': 'searchBug',
        'click #to-calendar': 'saveBug'
    }

    , render: function (event) {
        return this;
    }

    , searchBug: function (event) {
        if (event.which !== 13) { // ENTER_KEY = 13
            return;
        }
        if (!$(event.currentTarget).val().trim()) {
            app.Inited.foundBugsView.render({});
            return;
        }

        var number = $(event.currentTarget).val().trim();
        var badBugCollection = new BadBugCollection(number);
        badBugCollection.fetch({
            success: function (collection, response) {
                if (response && response.length > 0) {
                    app.Inited.foundBugsView.render(response);
                } else {
                    app.Inited.foundBugsView.focus();
                }
            }
            , error: function (collection, response) {
                alert(response.responseText);
            }
        });
    },

    saveBug: function (event) {
        event.preventDefault();

        var searchBug = $('#searchBug');
        if (!searchBug.val().trim()) {
            searchBug.focus();
            return;
        }

        var selectedBug = app.Inited.foundBugsView.getSelected();
        if (!selectedBug || selectedBug.length == 0) {
            app.Inited.foundBugsView.focus();
            return;
        }

        var selectedDep = app.Inited.foundBugsDepartmentView.getSelected();
        if (!selectedDep || selectedDep.length == 0) {
            app.Inited.foundBugsDepartmentView.focus();
            return;
        }
        var from = $('#from');
        if (!from.val().trim()) {
            from.datepicker("show");
            return;
        }

        var to = $("#to");
        if (!to.val().trim()) {
            to.datepicker("show");
            return;
        }

        var bugModel = new app.Models.BugModel(
            {
                bttBugId: selectedBug[0]
                , product: {department: {id: selectedDep[0]}}
                , start: from.val()
                , end: to.val()
            });

        bugModel.save({}, {
            success: function (model, respose, options) {
                $("#searchBug").val('');
                $("#from").val('');
                $("#to").val('');
                app.Inited.foundBugsView.render({});

                app.Inited.calendar.reFetchEvents();
            },
            error: function (model, xhr, options) {
                alert(xhr.responseText);
            }
        });
    }

});

var FoundBugsView = Backbone.View.extend({
    el: '#bugsFound'
    , template: 'bugs-options'
    , initialize: function () {
        this.$el.show();
        this.$el.chosen({max_selected_options: 1});
        return this;
    }
    , render: function (items) {
        var self = this;
        self.items = {'items': items};

        TemplateManager.get(this.template, function (template) {
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            self.$el.trigger("chosen:updated");
            //self.$el.trigger('chosen:activate');
            self.focus();
        });

        return this;
    }

    , focus: function () {
        this.$el.next('div').find('input').trigger('click');
    }
    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val() : [];
    }
});

var FoundBugsDepartmentView = Backbone.View.extend({
    el: '#bugsFoundDepartment'
    , template: 'options'
    , initialize: function (items) {
        this.items = {'items': items.toJSON()};
        this.render();
    }
    , render: function () {
        var self = this;

        TemplateManager.get(this.template, function (template) {
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            self.$el.chosen({max_selected_options: 1});
        });

        return this;
    }
    , focus: function () {
        this.$el.next('div').find('input').trigger('click');
    }

    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val() : [];
    }

});