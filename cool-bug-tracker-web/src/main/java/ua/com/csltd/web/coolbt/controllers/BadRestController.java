package ua.com.csltd.web.coolbt.controllers;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import java.util.List;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/ext"})
@Transactional(value = "badTransactionManager")
public class BadRestController {

    @Autowired
    public BaseBadDAO<User> userBadDAO;

    /*Read list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "users", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<User> selectUsers() {
        return userBadDAO.getSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("isDeleted", false))
                .add(Restrictions.eq("showAll", true))
                .list()
                ;
    }


}
