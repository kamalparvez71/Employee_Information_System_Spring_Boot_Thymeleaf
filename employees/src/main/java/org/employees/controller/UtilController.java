package org.employees.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.employees.entity.Employee;
import org.employees.entity.pojo.EmployeePojo;
import org.employees.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping("/utils")
public class UtilController {

    private Logger log = Logger.getLogger(EmployeesController.class);

    @Autowired
    private EmployeesRepository employeesRepository;

    @RequestMapping(value = "")
    public String util() {
        return "utils";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String callPojo(Model model) {

        model.addAttribute("employeePojo", new EmployeePojo());

        log.info("LLAMADO AL METODO GET");
        return "utils";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String callPojo(@ModelAttribute EmployeePojo employeePojo, Model model) {

        model.addAttribute("employeePojo", employeePojo);

        log.info("LLAMADO AL METODO POST lastName is: " + employeePojo.getLastName());

        model.addAttribute("search", employeesRepository.findByLastName(employeePojo.getLastName()));

        return "utils";
    }

    @RequestMapping(value = "/getEmployees/", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeesList(@RequestParam String lastName) {

        List<Employee> employeesList = employeesRepository.findByLastName(lastName);
        List<Employee> arrayList = new ArrayList();
        arrayList.addAll(employeesList);
        employeesList.clear();
        for (Employee emp : arrayList) {
            emp.setEmpNo(emp.getEmpNo());
            emp.setSalariesList(null);
            emp.setDeptEmpList(null);
            emp.setDeptManagerList(null);
            emp.setTitlesList(null);
            employeesList.add(emp);
        }
        return employeesList;
    }

}
