package ua.com.csltd.server.dao.badbtt.models.priority;

import ua.com.csltd.common.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author : verner
 * @since : 24.07.2015
 */
@Entity
@Table(name = "PRIORITY")
public class Priority extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_RUS")
    private String nameRus;

    @Column(name = "REPORT_NICKNAME")
    private String reportNickname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getReportNickname() {
        return reportNickname;
    }

    public void setReportNickname(String reportNickname) {
        this.reportNickname = reportNickname;
    }
}
