package ua.com.csltd.server.dao.badbtt.models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.com.csltd.common.models.BaseEntity;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import javax.persistence.*;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "PRODUCT")
public class BadProduct extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "ISDELETED")
    private Integer isDeleted;

    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MANAGER", referencedColumnName = "ID")
    private User manager;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
