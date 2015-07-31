package ua.com.csltd.common.models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@MappedSuperclass
public abstract class BadBaseEntity<ID> implements Serializable {

    @Id
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
