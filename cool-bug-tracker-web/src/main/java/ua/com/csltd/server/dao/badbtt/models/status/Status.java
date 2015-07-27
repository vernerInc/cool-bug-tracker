package ua.com.csltd.server.dao.badbtt.models.status;

import ua.com.csltd.common.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author : verner
 * @since : 22.07.2015
 */
@Entity
@Table(name = "STATUS")
public class Status extends BaseEntity<Long> {

    @Column(name = "NAME")
    public String name;

    @Column(name = "NAME_RUS")
    public String nameRu;

    @Column(name = "REPORT_NICKNAME")
    public String reportNickName;

    @Column(name = "REPORT_IS_ACTIVE")
    public String reportIsActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getReportNickName() {
        return reportNickName;
    }

    public void setReportNickName(String reportNickName) {
        this.reportNickName = reportNickName;
    }

    public String getReportIsActive() {
        return reportIsActive;
    }

    public void setReportIsActive(String reportIsActive) {
        this.reportIsActive = reportIsActive;
    }
}
