package ua.com.csltd.common.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@MappedSuperclass
public abstract class CoolBaseEntity<ID> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
