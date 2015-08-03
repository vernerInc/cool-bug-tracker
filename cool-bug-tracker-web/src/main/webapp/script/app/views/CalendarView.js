var CalendarView = Backbone.View.extend({
        el: '#calendar',
        heroes: {
            verner: {url: 'http://vk.com/doc157291948_409938932?hash=86062cc7a7cd763115&dl=bf19698e9c6e92ac5a&wnd=1',style: 'style="width: 120px"'}
            ,nikulin: {url: 'http://img0.joyreactor.cc/pics/post/%D0%B3%D0%B8%D1%84%D0%BA%D0%B8-%D0%BD%D0%B8%D0%BA%D1%83%D0%BB%D0%B8%D0%BD-%D0%B1%D0%B0%D0%BB%D0%B1%D0%B5%D1%81-211272.gif',style: 'style="width: 120px"'}
            , shepeleva : {url:'http://cdn.fishki.net/upload/post/201408/25/1297409/gallery/88bb93221f58c73eb80f508ac5bac3dd.gif',style: 'style="width: 150px"'}
        },
        initialize: function () {
            setInterval(this.reFetchEvents, 180000);
            this.render();
        },

        render: function () {
            var self = this;
            this.$el.fullCalendar({
                theme: true
                , lang: 'ru'
                , aspectRatio: 1.65
                , header: {
                    left: 'prev,next today',
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

                , loading: function (isLoading, view) {
                    if (!isLoading) {
                        self.tipLogins();
                    }
                }

                , eventRender: function (event, element) {
                    element.find('.fc-title').append(
                        '<a href="csbtt2://id=' + event.bttBugId + '">' +
                        event.product.name + ' ' +
                        event.bttBugNo + '</a>' + ' - ' +
                        event.description + '<br/>' +
                        'Responsible user: ' + '<a class="login">' + event.login + '</a>');
                }

                //,nextDayThreshold: "23:59:59"
            });

            return this;
        }

        , tipLogins: function () {
            var self = this;
            $(".qtip").remove();
            $(function () {
                    $("a.login").each(function () {
                        var login = this.text;
                        var url, style;
                        if (_.has(self.heroes, login)) {
                            var hero = self.heroes[login];
                            url = hero.url;
                            style = hero.style;
                        } else {
                            url = 'http://rainbow/cs/photo/thumb/' + login + '.jpg';
                        }

                        $(this).qtip({
                            content: {
                                text: function () {
                                    return '<img src="' + url + '" ' + (style ? style : '') + '>';
                                }
                            }
                        });
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