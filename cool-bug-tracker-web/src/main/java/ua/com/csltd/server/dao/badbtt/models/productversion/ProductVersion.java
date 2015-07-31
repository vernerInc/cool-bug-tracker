package ua.com.csltd.server.dao.badbtt.models.productversion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import ua.com.csltd.common.models.BadBaseEntity;
import ua.com.csltd.server.dao.badbtt.models.product.BadProduct;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "PRODUCT_VERSION")
public class ProductVersion extends BadBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID")
    private BadProduct product;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RELEASE_DATE")
    private Timestamp releaseDate;

    @Column(name = "ISDELETED")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDeleted;

    @Column(name = "ORDERNO")
    private Integer orderNo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARENT", referencedColumnName = "ID")
    private ProductVersion parentVersion;

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

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public ProductVersion getParentVersion() {
        return parentVersion;
    }

    public void setParentVersion(ProductVersion parentVersion) {
        this.parentVersion = parentVersion;
    }
}
