package ua.com.csltd.server.dao.coolbtt.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.com.csltd.common.models.BaseEntity;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;

import javax.persistence.*;

/**
 * @author : verner
 * @since : 23.07.2015
 */
@Entity
@Table(name = "PRODUCTS")
public class Product extends BaseEntity<Long> {


    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID")
    private Department department;

    @Column(name = "BTT_PRODUCT_ID")
    private Long bttProductId;


    @Column(name = "BTT_SUB_SYSTEM_ID")
    private Long bttSubSystemId;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getBttProductId() {
        return bttProductId;
    }

    public void setBttProductId(Long bttProductId) {
        this.bttProductId = bttProductId;
    }

    public Long getBttSubSystemId() {
        return bttSubSystemId;
    }

    public void setBttSubSystemId(Long bttSubSystemId) {
        this.bttSubSystemId = bttSubSystemId;
    }
}
