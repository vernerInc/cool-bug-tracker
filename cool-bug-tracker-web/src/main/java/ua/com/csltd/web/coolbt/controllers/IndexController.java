package ua.com.csltd.web.coolbt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@Controller
@RequestMapping({"/"})
public class IndexController {

    @Autowired
    private Environment env;

    @RequestMapping({"/", "/home"})
    @ResponseBody
    public ModelAndView home() {
        ModelAndView index = new ModelAndView("index");
        index.addObject("min", "dev".equals(env.getProperty("cool.btt.profile")) ? "" : ".min");
        return index;
    }
}
