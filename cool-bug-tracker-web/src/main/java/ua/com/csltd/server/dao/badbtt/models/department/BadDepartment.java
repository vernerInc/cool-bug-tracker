package ua.com.csltd.server.dao.badbtt.models.department;

import ua.com.csltd.common.models.BaseEntity;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import javax.persistence.*;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "DEPARTMENT")
public class BadDepartment extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

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
