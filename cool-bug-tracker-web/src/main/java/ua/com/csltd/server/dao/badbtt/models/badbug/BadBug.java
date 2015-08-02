package ua.com.csltd.server.dao.badbtt.models.badbug;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.com.csltd.common.models.BadBaseEntity;
import ua.com.csltd.common.models.CoolBaseEntity;
import ua.com.csltd.server.dao.badbtt.models.priority.Priority;
import ua.com.csltd.server.dao.badbtt.models.product.BadProduct;
import ua.com.csltd.server.dao.badbtt.models.productversion.ProductVersion;
import ua.com.csltd.server.dao.badbtt.models.status.Status;
import ua.com.csltd.server.dao.badbtt.models.subsystem.SubSystem;
import ua.com.csltd.server.dao.badbtt.models.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "Bugs")
public class BadBug extends BadBaseEntity<Long> {

    @Column(name = "BUG_NO")
    public Long bugNo;

    @Column(name = "BUG_DATE")
    public Timestamp bugDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID")
    public BadProduct product;

    @Column(name = "ID_BUG_TYPE")
    public Long bugTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STATUS", referencedColumnName = "ID")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRIORITY", referencedColumnName = "ID")
    private Priority priority;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCR")
    private String description;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "DEADLINE")
    private String deadLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SUBSYSTEM", referencedColumnName = "ID")
    private SubSystem system;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_IMPLVERSION", referencedColumnName = "ID")
    private ProductVersion productVersion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT", referencedColumnName = "ID")
    private BadBug project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARENT", referencedColumnName = "ID")
    private BadBug parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESPPERSON", referencedColumnName = "ID")
    private User respUser;

    public Long getBugNo() {
        return bugNo;
    }

    public void setBugNo(Long bugNo) {
        this.bugNo = bugNo;
    }

    public Timestamp getBugDate() {
        return bugDate;
    }

    public void setBugDate(Timestamp bugDate) {
        this.bugDate = bugDate;
    }

    public BadProduct getProduct() {
        return product;
    }

    public void setProduct(BadProduct product) {
        this.product = product;
    }

    public Long getBugTypeId() {
        return bugTypeId;
    }

    public void setBugTypeId(Long bugTypeId) {
        this.bugTypeId = bugTypeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public SubSystem getSystem() {
        return system;
    }

    public void setSystem(SubSystem system) {
        this.system = system;
    }

    public ProductVersion getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(ProductVersion productVersion) {
        this.productVersion = productVersion;
    }

    public BadBug getProject() {
        return project;
    }

    public void setProject(BadBug project) {
        this.project = project;
    }

    public BadBug getParent() {
        return parent;
    }

    public void setParent(BadBug parent) {
        this.parent = parent;
    }

    public User getRespUser() {
        return respUser;
    }

    public void setRespUser(User respUser) {
        this.respUser = respUser;
    }
}
