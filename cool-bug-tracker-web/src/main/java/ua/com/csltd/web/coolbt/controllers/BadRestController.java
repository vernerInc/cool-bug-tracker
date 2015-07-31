package ua.com.csltd.web.coolbt.controllers;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import ua.com.csltd.common.enums.BugStatus;
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.badbug.BadBug;
import ua.com.csltd.server.dao.badbtt.models.user.User;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestCommonException;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestError;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestResult;

import java.util.Arrays;
import java.util.List;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/ext"})
@Transactional(value = "badTransactionManager")
public class BadRestController {

    private static final Logger logger = LoggerFactory.getLogger(BadRestController.class);

    @Autowired
    public BaseBadDAO<User> userBadDAO;

    @Autowired
    public BaseBadDAO<BadBug> bugBadDAO;

    /*Read list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "users", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<User> selectUsers() {
        return userBadDAO.getSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("isDeleted", false))
                .add(Restrictions.eq("showAll", true))
                .addOrder(Order.asc("login"))
                .list()
                ;
    }

    /*Create of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bugs", method = RequestMethod.POST)
    public RestResult persistBug(BadBug bug) {
        bugBadDAO.persist(bug);
        return new RestResult(RestError.SUCCESS);
    }

    /*Read of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.GET)
    public BadBug selectBug(@PathVariable("id") final Long id) {
        return bugBadDAO.findById(id);
    }

    /*Read list of CRUD*/
    @SuppressWarnings("unchecked")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bugs/findBugByBugNo/{bugNo}", method = RequestMethod.GET)
    public List<BadBug> selectBugByBugNo(@PathVariable("bugNo") String bugNo) {
        Long lbugNo;
        try {
            lbugNo = NumberUtils.parseNumber(bugNo, Long.class);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RestCommonException(RestError.INCORRECT_NUMBER_VALUE);
        }

        return bugBadDAO.getSession()
                .createCriteria(BadBug.class)
                .add(Restrictions.eq("bugNo", lbugNo))
                .add(Restrictions.in("status.id", Arrays.asList(BugStatus.OPEN.id, BugStatus.AWAITING.id)))
                .list();
    }

    /*Read list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bugs", method = RequestMethod.GET)
    public List<BadBug> select() {
        return bugBadDAO.findAll();
    }

    /*Update of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bug/update", method = RequestMethod.PUT)
    public RestResult updateBug(BadBug bug) {
        bugBadDAO.update(bug);
        return new RestResult(RestError.SUCCESS);
    }

    /*Delete of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.DELETE)
    public RestResult deleteBug(@PathVariable("id") final Long id) {
        bugBadDAO.delete(id);
        return new RestResult(RestError.SUCCESS);
    }
}
