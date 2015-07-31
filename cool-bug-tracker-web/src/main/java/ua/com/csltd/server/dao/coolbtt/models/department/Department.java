package ua.com.csltd.server.dao.coolbtt.models.department;

import ua.com.csltd.common.models.CoolBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author : verner
 * @since : 23.07.2015
 */
@Entity
@Table(name = "DEPARTMENT")
@SequenceGenerator(name = "default_gen", sequenceName = "BTT.GEN_DEPARTMENT", allocationSize = 1)
public class Department extends CoolBaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
