package ua.com.csltd.web.coolbt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@Controller
@RequestMapping({"/"})
public class IndexController {

    @Autowired
    private BaseCoolDAO<Department> departmentCoolDAO;

    @RequestMapping({"/", "/home"})
    public ModelAndView home() {
        return new ModelAndView("index");
    }
}
