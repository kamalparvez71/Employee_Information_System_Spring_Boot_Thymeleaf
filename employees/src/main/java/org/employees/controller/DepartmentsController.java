package org.employees.controller;

import javax.sql.DataSource;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.employees.entity.Department;
import org.employees.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping(value = "/departments")
public class DepartmentsController {

    private Logger log = Logger.getLogger(DepartmentsController.class);

    private DepartmentsService departmentsService;

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void setDepartmentsService(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @RequestMapping("/new")
    public String newDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "departmentsForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveDepartment(@Valid Department department, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "departmentsForm";
        }
        departmentsService.saveDepartment(department);
        log.info("Department : " + "[" + department.getDeptNo() + "] " + "saved/updated");
        return "redirect:/departments/" + department.getDeptNo();
    }

    @RequestMapping("/{id}")
    public String showDepartment(@PathVariable String id, Model model) {
        model.addAttribute("department", departmentsService.getDepartmentById(id));
        return "departmentsShow";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("allDepartments", departmentsService.listAllDepartments());
        log.info("departments list: "+departmentsService.listAllDepartments());
        return "departments";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("department", departmentsService.getDepartmentById(id));
        return "departmentsForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        departmentsService.deleteDepartment(id);
        log.info("Department : " + "[" + id + "] " + "deleted permanently");
        return "redirect:/departments";
    }

    @ExceptionHandler()
    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ModelAndView getPdf() {

        JasperReportsPdfView view = new JasperReportsPdfView();

        view.setJdbcDataSource(dataSource);
        view.setUrl("classpath:/static/jasper/AllDepartments.jasper");
        view.setApplicationContext(appContext);
        return new ModelAndView(view);
    }

    @ExceptionHandler()
    @RequestMapping(value = "/excel.xlsx", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcel() {

        JasperReportsXlsView view = new JasperReportsXlsView();

        view.setJdbcDataSource(dataSource);
        view.setUrl("classpath:/static/jasper/AllDepartments.jasper");
        view.setApplicationContext(appContext);
        return new ModelAndView(view);
    }

}
