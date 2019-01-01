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
@Table(name = "salaries")
public class Salarie implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SalariePK salariesPK;
    @NotNull
    @Column(name = "salary")
    private int salary;
    @NotNull
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date toDate;
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employees;

    public Salarie() {
    }

    public Salarie(SalariePK salariesPK) {
        this.salariesPK = salariesPK;
    }

    public Salarie(SalariePK salariesPK, int salary, Date toDate) {
        this.salariesPK = salariesPK;
        this.salary = salary;
        this.toDate = toDate;
    }

    public Salarie(Integer empNo, Date fromDate) {
        this.salariesPK = new SalariePK(empNo, fromDate);
    }

    public SalariePK getSalariesPK() {
        return salariesPK;
    }

    public void setSalariesPK(SalariePK salariesPK) {
        this.salariesPK = salariesPK;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
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
        hash += (salariesPK != null ? salariesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salarie)) {
            return false;
        }
        Salarie other = (Salarie) object;
        if ((this.salariesPK == null && other.salariesPK != null) || (this.salariesPK != null && !this.salariesPK.equals(other.salariesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Salaries[ salariesPK=" + salariesPK + " ]";
    }
    
}
