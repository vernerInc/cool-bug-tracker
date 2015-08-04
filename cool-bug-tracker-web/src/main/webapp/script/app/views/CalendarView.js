var CalendarView = Backbone.View.extend({
        el: '#calendar'
        , isLoaded: false
        , initialize: function () {
            setInterval(this.reFetchEvents, 180000);
            this.render();
        }
        , render: function () {
            var self = this;
            this.$el.fullCalendar({
                theme: true
                , lang: 'ru'
                , aspectRatio: 1.65
                , header: {
                    left: 'prev,next,today',
                    center: 'title',
                    right: 'month basicWeek basicDay'
                }
                , weekends: true
                , fixedWeekCount: false
                , firstDay: 1
                , editable: true
                , resizable: true
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

                , viewRender: function () {
                    $(".qtip").remove();
                }

                , loading: function (isLoading, view) {
                    if (isLoading) {
                        $(".qtip").remove();
                    }
                }

                , eventRender: function (event, element) {
                    var find = element.find('.fc-title');

                    find.append(
                        '<a href="csbtt2://id=' + event.bttBugId + '">' +
                        event.product.name + ' ' +
                        event.bttBugNo + '</a>' + ' - ' +
                        event.description + '<br/>' +
                        titles.responsible + ': ');

                    var $a = $('<a class="login">' + event.login + '</a>');
                    find.append($a);
                    self.tipLogin(event.login, $a);
                }

                , eventResize: function (event, delta, revertFunc) {
                    var attributes = {
                        id: event.id
                        , start: event.start.format('DD.MM.YYYY')
                        , end: event.end.format('DD.MM.YYYY')
                    };
                    self.updateDuration(attributes, revertFunc);
                }
                , eventDrop: function (event, delta, revertFunc) {
                    var attributes = {
                        id: event.id
                        , start: event.start.format('DD.MM.YYYY')
                        , end: event.end.format('DD.MM.YYYY')
                    };
                    self.updateDuration(attributes, revertFunc);
                }
            });

            return this;
        }

        , updateDuration: function (attributes, revertFunc) {
            new app.Models.BugModel(attributes).save({}, {
                success: function (model, respose, options) {
                }
                , error: function (model, xhr, options) {
                    alert(xhr.responseText);
                    revertFunc();
                }
            });
        }

        , tipLogin: function (login, obj) {
            var self = this;
            var url, style;
            if (_.has(heroes, login)) {
                var hero = heroes[login];
                url = hero.url;
                style = hero.style;
            } else {
                url = 'http://rainbow/cs/photo/thumb/' + login + '.jpg';
            }

            $(obj).qtip({
                content: {
                    text: function () {
                        return '<img src="' + url + '" ' + (style ? style : '') + '>';
                    }
                }
            });
        }

        , tipLogins: function () {
            var self = this;
            $(".qtip").remove();
            $(function () {
                    $("a.login").each(function () {
                        var login = this.text;
                        self.tipLogin(login, this);
                    });
                }
            );
        }

        , getCurrentViewDates: function () {
            var view = this.$el.fullCalendar('getView');
            return {
                'start': view.intervalStart.format()
                , 'end': view.intervalEnd.format()
            };
        }
        ,
        reFetchEvents: function () {
            $('#calendar').fullCalendar('refetchEvents');
        }
    })
    ;


var heroes = {
    verner: {
        url: 'http://vk.com/doc157291948_409938932?hash=86062cc7a7cd763115&dl=bf19698e9c6e92ac5a&wnd=1',
        style: 'style="width: 120px"'
    }
    ,
    nikulin: {
        url: 'http://img0.joyreactor.cc/pics/post/%D0%B3%D0%B8%D1%84%D0%BA%D0%B8-%D0%BD%D0%B8%D0%BA%D1%83%D0%BB%D0%B8%D0%BD-%D0%B1%D0%B0%D0%BB%D0%B1%D0%B5%D1%81-211272.gif',
        style: 'style="width: 120px"'
    }
    ,
    shepeleva: {
        url: 'http://cs.pikabu.ru/images/big_size_comm/2012-11_6/1353924790803.gif',
        style: 'style="width: 200px"'
    }
}