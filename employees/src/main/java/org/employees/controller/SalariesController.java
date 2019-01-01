package org.employees.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.employees.entity.Employee;
import org.employees.entity.Salarie;
import org.employees.entity.SalariePK;
import org.employees.repository.EmployeesRepository;
import org.employees.service.SalariesService;
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
@RequestMapping(value = "/salaries")
public class SalariesController {

    private Logger log = Logger.getLogger(SalariesController.class);

    @Autowired
    private SalariesService salariesService;
    
    @Autowired
    EmployeesRepository employeesRepository;

    @RequestMapping("/new")
    public String newSalary(Model model) {
        model.addAttribute("salarie", new Salarie());
        return "salarieForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveSalary(@Valid Salarie salarie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "salarieForm";
        }
        salariesService.saveSalarie(salarie);
        log.info("Salary: " + "[" + salarie.getSalariesPK() + "] " + "saved/updated");
        return "redirect:/salaries/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showSalary(@PathVariable(value = "id") String id, @ModelAttribute SalariePK pk, Model model) {
        log.info("the id captured is: " + id);
        log.info("the pk captured is: " + pk);
        log.info("id length: " + id.length());

        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String fromDate0 = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date fromDate = fromDate1.parse(fromDate0);
            log.info("SimpleDateFormat fromDate1 is: " + fromDate1.format(fromDate));
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        log.info("the pk captured is after cast: " + pk);
        model.addAttribute("salarie", salariesService.getSalarieById(pk));
        return "salariesShow";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model, @PageableDefault(size = 25) Pageable pgbl) {
        model.addAttribute("allSalaries", salariesService.findAllSalarie(pgbl));
        model.addAttribute("pager", currentPage(pgbl));
        return "salaries";
    }

    private Pager currentPage(Pageable pgbl) {

        String baseUrl = "/salaries?page=";
        int currentIndex = pgbl.getPageNumber();
        int totalPageCount = salariesService.findAllSalarie(pgbl).getTotalPages();
        long totalItems = salariesService.findAllSalarie(pgbl).getTotalElements();

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
    public String edit(@PathVariable(value = "id") String id, @ModelAttribute SalariePK pk, Model model) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String fromDate0 = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = fromDate1.parse(fromDate0);
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        model.addAttribute("salarie", salariesService.getSalarieById(pk));
        return "salarieForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, @ModelAttribute SalariePK pk) {
        String[] twoStrings = id.split(",");
        String empNo0 = twoStrings[0];
        String fromDate0 = twoStrings[1];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = fromDate1.parse(fromDate0);
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        salariesService.deleteSalarie(pk);
        log.info("Salary: " + "[" + id + "] " + "deleted permanently");
        return "redirect:/salaries";
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
