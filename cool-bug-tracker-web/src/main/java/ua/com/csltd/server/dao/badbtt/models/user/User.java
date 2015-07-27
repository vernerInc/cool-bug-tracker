package ua.com.csltd.server.dao.badbtt.models.user;

import ua.com.csltd.common.models.BaseEntity;
import ua.com.csltd.server.dao.badbtt.models.department.BadDepartment;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "USERS")
public class User extends BaseEntity<Long> {

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boss", referencedColumnName = "ID")
    private User boss;

    @Column(name = "CREATINGTIME")
    private Timestamp creatingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTMENT", referencedColumnName = "ID")
    private BadDepartment department;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public User getBoss() {
        return boss;
    }

    public void setBoss(User boss) {
        this.boss = boss;
    }

    public Timestamp getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(Timestamp creatingTime) {
        this.creatingTime = creatingTime;
    }

    public BadDepartment getDepartment() {
        return department;
    }

    public void setDepartment(BadDepartment department) {
        this.department = department;
    }
}
