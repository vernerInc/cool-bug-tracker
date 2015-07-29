package ua.com.csltd.web.coolbt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.bug.Bug;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestCommonException;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestError;
import ua.com.csltd.web.coolbt.controllers.exceptions.RestResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@RestController
@RequestMapping({"/bug"})
@Transactional(value = "coolTransactionManager")
public class BugRestController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Autowired
    private BaseCoolDAO<Bug> bugCoolDAO;

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
    @SuppressWarnings("unchecked")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", /*params = {"userIds", "departmentIds", "productIds", "start", "end"}, */method = RequestMethod.GET)
    public List<Bug> selectBugList(
            @RequestParam(value = "userIds", required = false) List<Long> userIds
            , @RequestParam(value = "departmentIds", required = false) List<Long> departmentIds
            , @RequestParam(value = "productIds", required = false) List<Long> productIds
            , @RequestParam("start") Date start
            , @RequestParam("end") Date end

    ) {
        String sql = makeSql(start, end, userIds, departmentIds, productIds);
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

    private String makeSql(Date start, Date end, List<Long> userIds, List<Long> departmentIds, List<Long> productIds) {
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
