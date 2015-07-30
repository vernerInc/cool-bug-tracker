var LeftPanelActionsView = Backbone.View.extend({
    el: '#left-panel-actions'
    , template_bug_info: 'bug-info'

    , initialize: function () {
        app.Inited.foundBugsView = new FoundBugsView();
        this.render();
    }

    , events: {
        'keypress #searchBug': 'searchBug'
    }

    , render: function (event) {
        return this;
    }

    , searchBug: function (event) {
        if (event.which !== 13 || !$(event.currentTarget).val().trim()) { // ENTER_KEY = 13
            return;
        }

        var number = $(event.currentTarget).val().trim();
        var badBugCollection = new BadBugCollection(number);
        badBugCollection.fetch({
            success: function (collection, response) {
                if (response && response.length > 0) {
                    app.Inited.foundBugsView.render(response);
                } else {
                    alert('Found nothing');
                }
            }
            , error: function (collection, response) {
                alert(response.responseText);
            }
        });
    }


});

var FoundBugsView = Backbone.View.extend({
    el: '#bugsFound'
    , template: 'bugs-options'
    , initialize: function () {
        debugger;
        this.$el.show();
        this.$el.chosen({max_selected_options: 1});
        return this;
    }
    , render: function (items) {
        var self = this;
        self.items = {'items': items};
        debugger;

        TemplateManager.get(this.template, function (template) {
            debugger;
            self.view_template = _.template(template);
            self.$el.html(self.view_template(self.items));
            self.$el.trigger("chosen:updated");
            //self.$el.trigger('chosen:activate');
            self.$el.siblings('div').find('input').trigger('click');
            });

        return this;
    }
    , getSelected: function () {
        return this.$el.chosen().val() ? this.$el.chosen().val().toString() : '';
    }
});