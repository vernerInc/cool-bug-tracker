package ua.com.csltd.server.dao.badbtt.models.subsystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import ua.com.csltd.common.models.BadBaseEntity;
import ua.com.csltd.server.dao.badbtt.models.product.BadProduct;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import javax.persistence.*;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "SUBSYSTEM")
public class SubSystem extends BadBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID")
    private BadProduct product;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "ISDELETED")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDeleted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSIBLE", referencedColumnName = "ID")
    private User resposibleUser;

    @Column(name = "SUBSYSTEM_EMAIL")
    private String email;

    public BadProduct getProduct() {
        return product;
    }

    public void setProduct(BadProduct product) {
        this.product = product;
    }

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public User getResposibleUser() {
        return resposibleUser;
    }

    public void setResposibleUser(User resposibleUser) {
        this.resposibleUser = resposibleUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
