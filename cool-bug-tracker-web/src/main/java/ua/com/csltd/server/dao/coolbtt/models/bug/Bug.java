package ua.com.csltd.server.dao.coolbtt.models.bug;

import ua.com.csltd.common.models.BaseEntity;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author : verner
 * @since : 23.07.2015
 */
@Entity
@Table(name = "BUGS")
public class Bug extends BaseEntity<Long> {

    @Column(name = "BTT_BUG_ID")
    public Long bttBugId;

    @Column(name = "BTT_BUG_NO")
    public Long bttBugNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "START_DATE")
    public Timestamp startDate;

    @Column(name = "END_DATE")
    public Timestamp endDate;

    @Column(name = "IS_DELETED")
    public Integer isDeleted;

    @Column(name = "BTT_USER_RESPONSIBLE_ID")
    public Long userId;

    @Column(name = "BTT_USER_LOGIN")
    public String login;

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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
}
