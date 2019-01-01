package org.employees.entity.pojo;

import java.io.Serializable;

/**
 *
 * @author opalencia
 */
public class EmployeePojo implements Serializable {  

    private String gender;

    private String lastName;
    
    public EmployeePojo() {
    }

    public EmployeePojo(String gender, String lastName) {
        this.gender = gender;
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
     
            
}
