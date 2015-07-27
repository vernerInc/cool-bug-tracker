package ua.com.csltd.web.coolbt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.bug.Bug;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestError;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestResult;

import java.util.List;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/bug"})
public class BugRestController {

    @Autowired
    private BaseCoolDAO<Bug> bugCoolDAO;

    @Autowired
    private BaseCoolDAO<Department> departmentCoolDAO;

    /*Create of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestResult persistBug(Bug bug) {
        bugCoolDAO.persist(bug);
        return new RestResult(RestError.SUCCESS);
    }

    /*Read of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bug selectBug(@PathVariable("id") final Long id) {
        return bugCoolDAO.findById(id);
    }

    /*Read list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Bug> selectBugList() {
        return bugCoolDAO.findAll();
    }

    /*Update of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public RestResult updateBug(Bug bug) {
        bugCoolDAO.update(bug);
        return new RestResult(RestError.SUCCESS);
    }

    /*Delete of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResult deleteBug(@PathVariable("id") final Long id) {
        bugCoolDAO.delete(id);
        return new RestResult(RestError.SUCCESS);
    }

    /*Read deps list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public List<Department> selectDepsList() {
        return departmentCoolDAO.findAll();
    }

}
