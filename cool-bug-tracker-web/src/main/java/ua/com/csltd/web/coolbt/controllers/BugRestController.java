package ua.com.csltd.web.coolbt.controllers;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.badbug.BadBug;
import ua.com.csltd.server.dao.badbtt.models.subsystem.SubSystem;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.bug.Bug;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestCommonException;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestError;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestResult;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/bug"})
@Transactional(value = "coolTransactionManager")
public class BugRestController {

    private static final Logger logger = LoggerFactory.getLogger(BugRestController.class);


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Autowired
    private BaseCoolDAO<Bug> bugCoolDAO;

    @Autowired
    private BaseBadDAO<BadBug> bugBadDAO;

    @Autowired
    private BaseCoolDAO<Product> productCoolDAO;

    /*Create of CRUD*/
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public RestResult persistBug(@RequestBody Bug bug) {
        BadBug badBug = bugBadDAO.findById(bug.getBttBugId());

        if (badBug == null) {
            throw new RestCommonException(RestError.BUG_NOT_FOUND_IN_OLD_BTT);
        }

        bug.setBttBugNo(badBug.bugNo);
        bug.setUserId(badBug.getRespUser().getId());
        bug.setLogin(badBug.getRespUser().getLogin());
        bug.setIsDeleted(false);
        bug.setDescription(badBug.getTitle());

        /*adding to end date 23 h 59 min 59s */
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(bug.getEnd()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);
        bug.setEnd(cal.getTime());

        SubSystem system = badBug.getSystem();
        List<Product> products = productCoolDAO.getSession()
                .createCriteria(Product.class)
                .add(Restrictions.eq("bttProductId", system.getProduct().getId()))
                .add(Restrictions.eq("bttSubSystemId", system.getId()))
                .list();

        if (products == null || products.isEmpty()) {
            Product product = new Product();
            product.setName(system.getName());
            product.setDescription(system.getDescription());
            product.setDescription(system.getDescription());
            product.setBttProductId(system.getProduct().getId());
            product.setBttSubSystemId(system.getId());

            Department department = new Department();
            department.setId(bug.getProduct().getDepartment().getId());
            product.setDepartment(department);
            productCoolDAO.persist(product);
            bug.setProduct(product);
        } else {
            bug.setProduct(products.get(0));
        }

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
    @SuppressWarnings("unchecked")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Bug> selectBugList(
            @RequestParam(value = "userIds", required = false) List<Long> userIds
            , @RequestParam(value = "departmentIds", required = false) List<Long> departmentIds
            , @RequestParam(value = "productIds", required = false) List<Long> productIds
            , @RequestParam("start") Date start
            , @RequestParam("end") Date end

    ) {
        String sql = makeSql(userIds, departmentIds, productIds);
        Map<String, Object> params = makeParams(start, end, userIds, departmentIds, productIds);

        return bugCoolDAO.listBySql(sql, params);
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

    private String makeSql(List<Long> userIds, List<Long> departmentIds, List<Long> productIds) {
        StringBuilder sql = new StringBuilder()
                .append("FROM  Bug b\n")
                .append(" WHERE (:start BETWEEN b.start AND b.end\n")
                .append("   OR :end BETWEEN b.start AND b.end\n")
                .append("   OR b.start BETWEEN :start AND :end)\n");

        if (productIds != null && !productIds.isEmpty()) {
            sql.append("AND b.product.id IN (:productIds)\n");
        } else if (departmentIds != null && !departmentIds.isEmpty()) {
            sql.append("AND b.product.id IN (SELECT p.id FROM Product p WHERE p.department.id IN (:departmentIds))\n");
        }

        if (userIds != null && !userIds.isEmpty()) {
            sql.append("AND b.userId IN (:userIds)\n");
        }

        sql.append("AND b.isDeleted = :isDeleted");
        return sql.toString();
    }

    private Map<String, Object> makeParams(Date start, Date end, List<Long> userIds, List<Long> departmentIds, List<Long> productIds) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("start", start);
        params.put("end", end);

        if (productIds != null && !productIds.isEmpty()) {
            params.put("productIds", productIds);
        } else if (departmentIds != null && !departmentIds.isEmpty()) {
            params.put("departmentIds", departmentIds);
        }

        if (userIds != null && !userIds.isEmpty()) {
            params.put("userIds", userIds);
        }
        params.put("isDeleted", false);
        return params;
    }
}
