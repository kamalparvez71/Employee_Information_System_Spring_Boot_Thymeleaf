package org.employees.controller;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.employees.entity.Department;
import org.employees.entity.DeptManager;
import org.employees.entity.DeptManagerPK;
import org.employees.entity.Employee;
import org.employees.repository.EmployeesRepository;
import org.employees.service.DepartmentsService;
import org.employees.service.DeptManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping(value = "/deptmanagers")
public class DeptManagerController {

    private Logger log = Logger.getLogger(DeptManagerController.class);

    @Autowired
    private DeptManagerService deptManagerService;

    @Autowired
    private DepartmentsService departmentsService;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSource dataSource;
    @Autowired
    EmployeesRepository employeesRepository;

    @RequestMapping("/new")
    public String newDepartmentMan(Model model) {
        model.addAttribute("deptManager", new DeptManager());
        return "deptManagerForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveDepartmentMan(@Valid DeptManager deptMan, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "deptManagerForm";
        }
        deptManagerService.saveDeptManager(deptMan);
        log.info("Department Manager : " + "[" + deptMan.getDeptManagerPK() + "] " + "saved/updated");
        return "redirect:/deptmanagers/"+ deptMan.getDeptManagerPK();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showDepartmentManager(@PathVariable(value = "id") String id, @ModelAttribute DeptManagerPK pk, Model model) {
        log.info("the id captured is: " + id);
        log.info("the pk captured is: " + pk);
        log.info("id length: " + id.length());

        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String deptNo = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);
        pk.setDeptNo(deptNo);

        log.info("empNo: " + empNo);
        log.info("deptNo: " + deptNo);

        log.info("the pk captured is after cast: " + pk);
        model.addAttribute("deptManager", deptManagerService.getDeptManagerById(pk));
        return "deptManagerShow";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("allDeptMan", deptManagerService.listAllDeptManager());
        return "deptManagers";
    }

    @ModelAttribute("allDepartments")
    public Iterable<Department> populateDepartments() {
        return departmentsService.listAllDepartments();
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") String id, @ModelAttribute DeptManagerPK pk, Model model) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String deptNo = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);
        pk.setDeptNo(deptNo);
        model.addAttribute("deptManager", deptManagerService.getDeptManagerById(pk));
        return "deptManagerForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, @ModelAttribute DeptManagerPK pk) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String deptNo = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);
        pk.setDeptNo(deptNo);
        deptManagerService.deleteDeptManager(pk);
        log.info("Department Manager: " + "[" + id + "] " + "deleted permanently");
        return "redirect:/deptmanagers";
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
    
     @ExceptionHandler()
    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ModelAndView getPdf() {

        JasperReportsPdfView view = new JasperReportsPdfView();

        view.setJdbcDataSource(dataSource);
        view.setUrl("classpath:/static/jasper/AllManagers.jasper");
        view.setApplicationContext(appContext);
        return new ModelAndView(view);
    }

    @ExceptionHandler()
    @RequestMapping(value = "/excel.xlsx", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcel() {

        JasperReportsXlsView view = new JasperReportsXlsView();

        view.setJdbcDataSource(dataSource);
        view.setUrl("classpath:/static/jasper/AllManagers.jasper");
        view.setApplicationContext(appContext);
        return new ModelAndView(view);
    }
}
