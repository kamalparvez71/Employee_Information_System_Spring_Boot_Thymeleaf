package org.employees.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author opalencia
 */
@Embeddable
public class SalariePK implements Serializable {

    @NotNull
    @Column(name = "emp_no")
    private Integer empNo;
    @NotNull
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date fromDate;

    public SalariePK() {
    }

    public SalariePK(Integer empNo, Date fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.empNo);
        hash = 23 * hash + Objects.hashCode(this.fromDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalariePK other = (SalariePK) obj;
        if (!Objects.equals(this.empNo, other.empNo)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + empNo + "," + fromDate + "";
    }

}
