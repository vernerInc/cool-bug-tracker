package ua.com.csltd.server.dao.coolbtt.models.bug;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import ua.com.csltd.common.models.CoolBaseEntity;
import ua.com.csltd.common.utils.CustomDateDeSerialization;
import ua.com.csltd.common.utils.CustomDateSerializer;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : verner
 * @since : 23.07.2015
 */
@Entity
@Table(name = "BUGS")
@SequenceGenerator(name = "default_gen", sequenceName = "BTT.GEN_BUGS", allocationSize = 1)
public class Bug extends CoolBaseEntity<Long> {

    @Column(name = "BTT_BUG_ID", updatable = false)
    public Long bttBugId;

    @Column(name = "BTT_BUG_NO", updatable = false)
    public Long bttBugNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID", updatable = false)
    private Product product;


    @Column(name = "START_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeSerialization.class)
    private Date start;

    @Column(name = "END_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeSerialization.class)
    private Date end;

    @Column(name = "IS_DELETED")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean deleted;

    @Column(name = "BTT_USER_RESPONSIBLE_ID")
    private Long userId;

    @Column(name = "BTT_USER_LOGIN")
    private String login;

    @Column(name = "DESCRIPTION", updatable = false)
    private String description;

    public Long getBttBugId() {
        return bttBugId;
    }

    public void setBttBugId(Long bttBugId) {
        this.bttBugId = bttBugId;
    }

    public Long getBttBugNo() {
        return bttBugNo;
    }

    public void setBttBugNo(Long bttBugNo) {
        this.bttBugNo = bttBugNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAllDay() {
        return true;
    }

}
