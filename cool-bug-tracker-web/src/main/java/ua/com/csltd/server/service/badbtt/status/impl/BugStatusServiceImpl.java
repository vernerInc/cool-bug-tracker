package ua.com.csltd.server.service.badbtt.status.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.status.Status;
import ua.com.csltd.server.service.badbtt.status.BugStatusService;

import java.util.List;

/**
 * @author : verner
 * @since : 22.07.2015
 */
@Service
@Transactional(value = "badTransactionManager")
public class BugStatusServiceImpl implements BugStatusService {

    @Autowired
    private BaseBadDAO<Status> statusBadDAO;


    public List<Status> list() {
        return statusBadDAO.findAll();
    }
}
