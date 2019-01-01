package org.employees.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author opalencia
 */
@Entity
@Table(name = "titles")
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TitlePK titlesPK;
    @NotNull
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date toDate;
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employees;

    public Title() {
    }

    public Title(TitlePK titlesPK) {
        this.titlesPK = titlesPK;
    }

    public Title(int empNo, String title, Date fromDate) {
        this.titlesPK = new TitlePK(empNo, title, fromDate);
    }

    public TitlePK getTitlesPK() {
        return titlesPK;
    }

    public void setTitlesPK(TitlePK titlesPK) {
        this.titlesPK = titlesPK;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Employee getEmployees() {
        return employees;
    }

    public void setEmployees(Employee employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (titlesPK != null ? titlesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.titlesPK == null && other.titlesPK != null) || (this.titlesPK != null && !this.titlesPK.equals(other.titlesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Titles[ titlesPK=" + titlesPK + " ]";
    }
    
}
