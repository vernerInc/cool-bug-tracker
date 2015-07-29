package ua.com.csltd.web.coolbt.controllers;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;

import java.util.List;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/products"})
@Transactional(value = "coolTransactionManager")
public class ProductRestController {

    @Autowired
    private BaseCoolDAO<Product> productCoolDAO;

    /*Read products list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> selectProductsList() {
        return productCoolDAO.findAll();
    }

    /*Read products list of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{departmentIds}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<Product> selectProductsList(@PathVariable("departmentIds") List<Long> departmentIds) {
        return productCoolDAO.getSession()
                .createCriteria(Product.class)
                .add(Restrictions.in("department.id", departmentIds)).addOrder(Order.asc("id"))
                .list();
    }
}
