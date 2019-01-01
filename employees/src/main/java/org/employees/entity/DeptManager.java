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
@Table(name = "dept_manager")
public class DeptManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DeptManagerPK deptManagerPK;
    @NotNull
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date fromDate;
    @NotNull
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date toDate;
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employees;
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Department departments;

    public DeptManager() {
    }

    public DeptManager(DeptManagerPK deptManagerPK) {
        this.deptManagerPK = deptManagerPK;
    }

    public DeptManager(DeptManagerPK deptManagerPK, Date fromDate, Date toDate) {
        this.deptManagerPK = deptManagerPK;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptManager(int empNo, String deptNo) {
        this.deptManagerPK = new DeptManagerPK(empNo, deptNo);
    }

    public DeptManagerPK getDeptManagerPK() {
        return deptManagerPK;
    }

    public void setDeptManagerPK(DeptManagerPK deptManagerPK) {
        this.deptManagerPK = deptManagerPK;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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

    public Department getDepartments() {
        return departments;
    }

    public void setDepartments(Department departments) {
        this.departments = departments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deptManagerPK != null ? deptManagerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeptManager)) {
            return false;
        }
        DeptManager other = (DeptManager) object;
        if ((this.deptManagerPK == null && other.deptManagerPK != null) || (this.deptManagerPK != null && !this.deptManagerPK.equals(other.deptManagerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeptManager[ deptManagerPK=" + deptManagerPK + " ]";
    }
    
}
