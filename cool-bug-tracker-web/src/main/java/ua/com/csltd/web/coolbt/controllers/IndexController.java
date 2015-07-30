package ua.com.csltd.web.coolbt.controllers;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.com.csltd.common.utils.Json;
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.user.User;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;

import java.io.IOException;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@Controller
@RequestMapping({"/"})
@Transactional(value = "badTransactionManager")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private BaseCoolDAO<Department> departmentCoolDAO;

    @Autowired
    public BaseBadDAO<User> userBadDAO;

    @Autowired
    public BaseCoolDAO<Product> productCoolDAO;

    @Autowired
    private Json json;

    @Autowired
    private Environment env;

    @RequestMapping({"/", "/home", "/index"})
    @ResponseBody
    public ModelAndView home() {
        ModelAndView index = new ModelAndView("index");
        index.addObject("min", "dev".equals(env.getProperty("cool.btt.profile")) ? "" : ".min");
        try {
            index.addObject("departments", json.objectToString(departmentCoolDAO.findAll()));
            index.addObject("users", json.objectToString(userBadDAO.getSession()
                            .createCriteria(User.class)
                            .add(Restrictions.eq("isDeleted", false))
                            .add(Restrictions.eq("showAll", true))
                            .addOrder(Order.asc("login"))
                            .list()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return index;
    }
}
