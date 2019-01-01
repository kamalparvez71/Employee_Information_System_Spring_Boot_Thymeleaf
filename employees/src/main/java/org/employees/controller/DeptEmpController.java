package org.employees.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.employees.entity.Department;
import org.employees.entity.DeptEmp;
import org.employees.entity.DeptEmpPK;
import org.employees.entity.Employee;
import org.employees.repository.EmployeesRepository;
import org.employees.service.DepartmentsService;
import org.employees.service.DeptEmployeeService;
import org.employees.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping(value = "/deptemployees")
public class DeptEmpController {

    private Logger log = Logger.getLogger(DeptEmpController.class);

    private DeptEmployeeService deptEmployeeService;

    private DepartmentsService departmentsService;

    private EmployeesRepository employeesRepository;

    @Autowired
    public void setDepartmentsService(DepartmentsService departmentsService,
            DeptEmployeeService deptEmployeeService, EmployeesRepository employeesRepository) {
        this.departmentsService = departmentsService;
        this.deptEmployeeService = deptEmployeeService;
        this.employeesRepository = employeesRepository;
    }

    @RequestMapping("/new")
    public String newDepartmentEmp(Model model) {
        model.addAttribute("deptEmp", new DeptEmp());
        return "deptEmployeesForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveDepartmentEmp(@Valid DeptEmp deptEmp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "deptEmployeesForm";
        }
        deptEmployeeService.saveDeptEmp(deptEmp);
        log.info("Department Employee : " + "[" + deptEmp.getDeptEmpPK() + "] " + "saved/updated");
        return "redirect:/deptemployees/" + deptEmp.getDeptEmpPK();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showDepartmentEmployee(@PathVariable(value = "id") String id, @ModelAttribute DeptEmpPK pk, Model model) {
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
        model.addAttribute("deptEmp", deptEmployeeService.getDeptEmpById(pk));
        return "deptEmployeesShow";
    }

    //INTENTOS FALLIDOS (-_-)
//    public String showDepartmentEmployee(@PathVariable("id") DeptEmpPK id, Model model) {...} //ESTO SERIA LO IDEAL, PERO NO ES CAPAZ DE CONVERTIR IMPLICITAMENTE AL OBJETO DeptEmpPK
//    public String showDepartmentEmployee(@MatrixVariable(name="id", pathVar="id") DeptEmpPK id, Model model) {...}
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model, @PageableDefault(size = 25) Pageable pgbl) {
        model.addAttribute("allDeptEmp", deptEmployeeService.findAllDeptEmp(pgbl));
        model.addAttribute("pager", currentPage(pgbl));
        return "deptEmployees";
    }

    @ModelAttribute("allDepartments")
    public Iterable<Department> populateDepartments() {
        return departmentsService.listAllDepartments();
    }

    private Pager currentPage(Pageable pgbl) {

        String baseUrl = "/deptemployees?page=";
        int currentIndex = pgbl.getPageNumber();
        int totalPageCount = deptEmployeeService.findAllDeptEmp(pgbl).getTotalPages();
        long totalItems = deptEmployeeService.findAllDeptEmp(pgbl).getTotalElements();

        Pager pager = new Pager();

        if (currentIndex > totalPageCount) {
            pager.setCurrentIndex(totalPageCount);
        } else {
            pager.setCurrentIndex(currentIndex);
        }

        pager.setTotalPageCount(totalPageCount - 1);
        pager.setTotalItems(totalItems);
        pager.setBaseUrl(baseUrl);
        return pager;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") String id, @ModelAttribute DeptEmpPK pk, Model model) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String deptNo = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);
        pk.setDeptNo(deptNo);
        model.addAttribute("deptEmp", deptEmployeeService.getDeptEmpById(pk));
        return "deptEmployeesForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, @ModelAttribute DeptEmpPK pk) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String deptNo = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);
        pk.setDeptNo(deptNo);
        deptEmployeeService.deleteDeptEmp(pk);
        log.info("Department Employee: " + "[" + id + "] " + "deleted permanently");
        return "redirect:/deptemployees";
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
        System.out.println("lastName: " + lastName);
        return employeesList;
    }


}
