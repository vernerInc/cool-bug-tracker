<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <link rel='stylesheet' href='script/libs/fullcalendar/lib/cupertino/jquery-ui.min.css'/>
    <link href='script/libs/fullcalendar/fullcalendar<c:out value="${min}"/>.css' rel='stylesheet'/>
    <link href='script/libs/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print'/>

    <script src='script/libs/fullcalendar/lib/moment.min.js'></script>
    <script src='script/libs/jquery/jquery-1.11.3<c:out value="${min}"/>.js'></script>
    <script src='script/libs/fullcalendar/lib/jquery-ui.custom.min.js'></script>
    <script src='script/libs/fullcalendar/fullcalendar<c:out value="${min}"/>.js'></script>
    <script src='script/libs/fullcalendar/lang-all.js'></script>

    <script src='script/libs/json/json2.js'></script>
    <script src='script/libs/underscore/underscore<c:out value="${min}"/>.js'></script>
    <script src='script/libs/backbone/backbone-1.2.1<c:out value="${min}"/>.js'></script>
    <script src='script/libs/mustache/mustache<c:out value="${min}"/>.js'></script>
    <script src='script/libs/icanhaz/ICanHaz.min.js'></script>

    <link rel="stylesheet" href="script/libs/chosen/docsupport/prism.css">
    <script src='script/libs/chosen/docsupport/prism.js'></script>

    <link rel="stylesheet" href="script/libs/chosen/chosen<c:out value="${min}"/>.css">
    <script src='script/libs/chosen/chosen.jquery<c:out value="${min}"/>.js'></script>

    <script src='script/libs/jquerystorageapi/jquery.storageapi<c:out value="${min}"/>.js'></script>

    <script src='script/app/views/AppViews.js'></script>
    <script src='script/app/models/AppModels.js'></script>
    <script src='script/app/models/AppCollections.js'></script>

    <link href='css/main-app.css' rel='stylesheet'/>

</head>
<body>

<div id='wrap'>

    <div id='calendar'></div>

    <div id='left-panel-filters' class="left-panel">
        <h4>Filters</h4>

        <select data-placeholder="Select Department" multiple class="chosen-select"
                style="width:350px; display: none"
                tabindex="18"
                id="departments">
        </select>

        <select data-placeholder="Select Products" multiple class="chosen-select"
                style="width:350px; display: none"
                tabindex="18"
                id="products">
        </select>

        <select data-placeholder="Select User" multiple class="chosen-select"
                style="width:350px; display: none"
                tabindex="18"
                id="users">
        </select>

    </div>

    <div id='left-panel-actions' class="left-panel left-panel-actions">
        <h4>Actions</h4>

        <div class='fc-event choose-filter'>Find by BTT number</div>
    </div>

    <div style='clear:both'></div>

</div>

</body>

<script>
    var app = {};
    var storages = {
        departments: 'departments'
        , products: 'products'
        , users: 'users'
    };
    var StorageManager = {
        get: function (storageName) {
            if (!$.localStorage.get(storageName)) {
                this.set(storageName, []);
            }
            return $.localStorage.get(storageName)
        },

        set: function (storageName, object) {
            $.localStorage.set(storageName, object);
        }
    };

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

    // create namespace for our app
    app.Views = {};
    app.Models = {};
    app.Collections = {};

    app.Views.CalendarView = CalendarView;
    app.Views.DepartmentView = DepartmentView;
    app.Views.ProductsView = ProductsView;
    app.Views.UsersView = UsersView;

    app.Collections.DepartmentsCollection = DepartmentsCollection;
    app.Collections.ProductsCollection = ProductsCollection;
    app.Collections.UsersCollections = UsersCollection;

    /*initilize all successively*/
    app.Inited = {};

    /*Init departments*/
    var initDepartments = function (/*initProducts, initUsersCallBack, initCalendarCallBack*/) {
        app.Inited.departments = new app.Collections.DepartmentsCollection();
        app.Inited.departments.reset(<c:out value="${departments}" escapeXml="false"/>);
        app.Inited.departmentView = new app.Views.DepartmentView(app.Inited.departments/*, initProductsCallBack, initUsersCallBack, initCalendarCallBack*/);
    };

    /*Init products*/
    var initProductsCallBack = function (/*initUsersCallBack, initCalendarCallBack*/) {
        app.Inited.products = new app.Collections.ProductsCollection(/*initUsersCallBack, initCalendarCallBack*/);
    };


    /*Init user*/
    var initUsersCallBack = function (/*initCalendarCallBack*/) {
        app.Inited.users = new app.Collections.UsersCollections();
        app.Inited.users.reset(<c:out value="${users}" escapeXml="false"/>);
        app.Inited.userView = new app.Views.UsersView(app.Inited.users/*, initCalendarCallBack*/);
    };

    /*Init calendar*/
    var initCalendarCallBack = function () {
        app.Inited.calendar = new app.Views.CalendarView();
    };


    $(document).ready(function () {
        initDepartments();
        /*after initProducts and another ones init on an each callback*/
    });

</script>
</html>