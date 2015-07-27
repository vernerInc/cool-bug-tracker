package ua.com.csltd.server.service.badbtt.status;

import ua.com.csltd.server.dao.badbtt.models.status.Status;

import java.util.List;

/**
 * @author : verner
 * @since : 22.07.2015
 */
public interface BugStatusService {

    List<Status> list();
}
