package org.employees.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author opalencia
 */
@Embeddable
public class DeptManagerPK implements Serializable {

    @NotNull
    @Column(name = "emp_no")
    private Integer empNo;
    @NotNull
    @Column(name = "dept_no")
    private String deptNo;

    public DeptManagerPK() {
    }

    public DeptManagerPK(Integer empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.empNo);
        hash = 37 * hash + Objects.hashCode(this.deptNo);
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
        final DeptManagerPK other = (DeptManagerPK) obj;
        if (!Objects.equals(this.deptNo, other.deptNo)) {
            return false;
        }
        if (!Objects.equals(this.empNo, other.empNo)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return ""+empNo+","+deptNo+"";
    }
    
}
