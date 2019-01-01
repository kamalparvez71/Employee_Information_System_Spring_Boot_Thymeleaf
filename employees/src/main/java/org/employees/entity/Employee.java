package org.employees.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author opalencia
 */
@Entity
@Table(name = "employees")
//@NamedQueries({
//    @NamedQuery(name = "Employee.findByLastName", 
//            query = "SELECT e.empNo, e.firstName, e.lastName, e.gender, e.birthDate, e.hireDate FROM Employee e WHERE e.lastName LIKE LOWER(CONCAT('%', :lastName, '%')) ORDER BY e.empNo")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "emp_no")
    private Integer empNo;
    @NotNull
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthDate;
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Column(name = "gender")
    private String gender;
    @NotNull
    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date hireDate;
    @NotNull
    @Lob
    @Column(name = "photo", length = Integer.MAX_VALUE, nullable = true)
    private byte[] photo;
//    private CommonsMultipartFile fileData;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employees")
    private List<Salarie> salariesList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employees")
    private List<DeptEmp> deptEmpList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employees")
    private List<DeptManager> deptManagerList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employees")
    private List<Title> titlesList;

    public Employee() {
    }

    public Employee(Integer empNo) {
        this.empNo = empNo;
    }

    public Employee(Integer empNo, Date birthDate, String firstName, String lastName, String gender, Date hireDate, byte[] photo) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
        this.photo = photo;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
//
//    public CommonsMultipartFile getFileData() {
//        return fileData;
//    }
//
//    public void setFileData(CommonsMultipartFile fileData) {
//        this.fileData = fileData;
//    }

    public List<Salarie> getSalariesList() {
        return salariesList;
    }

    public void setSalariesList(List<Salarie> salariesList) {
        this.salariesList = salariesList;
    }

    public List<DeptEmp> getDeptEmpList() {
        return deptEmpList;
    }

    public void setDeptEmpList(List<DeptEmp> deptEmpList) {
        this.deptEmpList = deptEmpList;
    }

    public List<DeptManager> getDeptManagerList() {
        return deptManagerList;
    }

    public void setDeptManagerList(List<DeptManager> deptManagerList) {
        this.deptManagerList = deptManagerList;
    }

    public List<Title> getTitlesList() {
        return titlesList;
    }

    public void setTitlesList(List<Title> titlesList) {
        this.titlesList = titlesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empNo != null ? empNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.empNo == null && other.empNo != null) || (this.empNo != null && !this.empNo.equals(other.empNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee[ empNo=" + empNo + " ]";
    }

}
