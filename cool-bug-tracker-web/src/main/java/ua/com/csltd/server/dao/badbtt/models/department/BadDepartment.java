package ua.com.csltd.server.dao.badbtt.models.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.com.csltd.common.models.BadBaseEntity;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import javax.persistence.*;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "DEPARTMENT")
public class BadDepartment extends BadBaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTLEADER", referencedColumnName = "ID")
    private User leader;

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

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }
}
