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
import org.employees.entity.Title;
import org.employees.entity.TitlePK;
import org.employees.repository.EmployeesRepository;
import org.employees.service.TitlesService;
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
@RequestMapping(value = "/titles")
public class TitlesController {

    private Logger log = Logger.getLogger(TitlesController.class);

    @Autowired
    private TitlesService titlesService;
    
    @Autowired
    EmployeesRepository employeesRepository;

    @RequestMapping("/new")
    public String newTitle(Model model) {
        model.addAttribute("title", new Title());
        return "titleForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveTitle(@Valid Title title, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "titleForm";
        }
        titlesService.saveTitle(title);
        log.info("Title: " + "[" + title.getTitlesPK() + "] " + "saved/updated");
        return "redirect:/titles/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showTitle(@PathVariable(value = "id") String id, @ModelAttribute TitlePK pk, Model model) {
        log.info("the id captured is: " + id);
        log.info("the pk captured is: " + pk);
        log.info("id length: " + id.length());

        String[] threeStrings = id.split(",");
        String empNo0 = threeStrings[0];
        String title0 = threeStrings[1];
        String fromDate0 = threeStrings[2];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("y-M-d", Locale.US);
            Date fromDate = fromDate1.parse(fromDate0);
            log.info("SimpleDateFormat fromDate1 is: " + fromDate1.format(fromDate));
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        pk.setTitle(title0);

        log.info("the pk captured is after cast: " + pk);
        model.addAttribute("title", titlesService.getTitleById(pk));
        return "titleShow";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model, @PageableDefault(size = 25) Pageable pgbl) {
        model.addAttribute("allTitles", titlesService.findAllTitle(pgbl));
        model.addAttribute("pager", currentPage(pgbl));
        return "titles";
    }

    private Pager currentPage(Pageable pgbl) {

        String baseUrl = "/titles?page=";
        int currentIndex = pgbl.getPageNumber();
        int totalPageCount = titlesService.findAllTitle(pgbl).getTotalPages();
        long totalItems = titlesService.findAllTitle(pgbl).getTotalElements();

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
    public String edit(@PathVariable(value = "id") String id, @ModelAttribute TitlePK pk, Model model) {
        String[] threeStrings = id.split(",");
        String empNo0 = threeStrings[0];
        String title0 = threeStrings[1];
        String fromDate0 = threeStrings[2];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("y-M-d", Locale.US);
            Date fromDate = fromDate1.parse(fromDate0);
            log.info("SimpleDateFormat fromDate1 is: " + fromDate1.format(fromDate));
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        pk.setTitle(title0);

        model.addAttribute("title", titlesService.getTitleById(pk));
        return "titleForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, @ModelAttribute TitlePK pk) {
        String[] threeStrings = id.split(",");
        String empNo0 = threeStrings[0];
        String title0 = threeStrings[1];
        String fromDate0 = threeStrings[2];

        int empNo = Integer.parseInt(empNo0);
        pk.setEmpNo(empNo);

        try {
            SimpleDateFormat fromDate1 = new SimpleDateFormat("y-M-d", Locale.US);
            Date fromDate = fromDate1.parse(fromDate0);
            log.info("SimpleDateFormat fromDate1 is: " + fromDate1.format(fromDate));
            pk.setFromDate(fromDate);
        } catch (ParseException pe) {
            System.out.println("parse exception: " + pe);
        }

        pk.setTitle(title0);

        titlesService.deleteTitle(pk);
        log.info("Title: " + "[" + id + "] " + "deleted permanently");
        return "redirect:/titles";
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
