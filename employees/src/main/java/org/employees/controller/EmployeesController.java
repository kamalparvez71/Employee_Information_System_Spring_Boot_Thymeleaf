package org.employees.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import org.employees.util.Pager;
import org.apache.log4j.Logger;
import org.employees.entity.Employee;
import org.employees.entity.pojo.EmployeePojo;
import org.employees.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping(value = "/employees")
public class EmployeesController {

    private Logger log = Logger.getLogger(EmployeesController.class);
    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSource dataSource;

    public void setEmployeesService(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employeesForm";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveEmployee(@Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employeesForm";
        }
        employeesService.saveEmployee(employee);
        log.info("Employee : " + "[" + employee.getEmpNo() + "] " + "saved/updated");
        return "redirect:/employees/" + employee.getEmpNo();
    }

    @ExceptionHandler()
    @RequestMapping("/{id}")
    public String showEmployee(@PathVariable Integer id, Model model) {

        model.addAttribute("employee", employeesService.getEmployeeById(id));
        System.out.println("id: " + id);
        return "employeesShow";
    }

    @RequestMapping(value = "/image/{photo_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPhoto(@PathVariable("photo_id") long photoid) throws IOException {
//        byte[] imageContent=//get image from DAO based on id
        byte[] imageContent = null;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/employees")
    public ModelAndView employeeImage() throws IOException {
        ModelAndView view = new ModelAndView("employees");
        view.addObject("photo_id", employeesService.getEmployeeById(Integer.SIZE));
        return view;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model model, Pageable pgbl) {
        model.addAttribute("ep", new EmployeePojo());
        model.addAttribute("allEmployees", employeesService.findAll(pgbl));
        model.addAttribute("pager", currentPage(pgbl));

        System.out.println("currentIndex: " + currentPage(pgbl).getCurrentIndex());
        System.out.println("totalItems: " + employeesService.findAll(pgbl).getTotalElements());
        System.out.println("totalPageCount: " + employeesService.findAll(pgbl).getTotalPages());
        return "employees";
    }

    private Pager currentPage(Pageable pgbl) {

        String baseUrl = "/employees?page=";
        int currentIndex = pgbl.getPageNumber();
        int totalPageCount = employeesService.findAll(pgbl).getTotalPages();
        long totalItems = employeesService.findAll(pgbl).getTotalElements();

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
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("employee", employeesService.getEmployeeById(id));
        return "employeesForm";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        employeesService.deleteEmployee(id);
        log.info("Employee : " + "[" + id + "] " + "deleted permanently");
        return "redirect:/employees";
    }

//    @RequestMapping(value = {"/employeePhoto"}, method = RequestMethod.GET)
//    public void employeePhoto(HttpServletRequest request, HttpServletResponse response, Model model,
//            @RequestParam("empNo") String empNo) throws IOException {
//        Employee employee = null;
//        if (empNo != null) {
//            employee = this.employeesService.getEmployeeById(Integer.SIZE);
//        }
//        if (employee != null && employee.getPhoto() != null) {
//            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//            response.getOutputStream().write(employee.getPhoto());
//        }
//        response.getOutputStream().close();
//    }
    @ExceptionHandler()
    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ModelAndView getPdf(@ModelAttribute EmployeePojo ep, Model model) {

        model.addAttribute("ep", ep);

        JasperReportsPdfView view = new JasperReportsPdfView();

        view.setJdbcDataSource(dataSource);
        if (ep.getGender().equals("A")) {
            view.setUrl("classpath:/static/jasper/AllEmployees.jasper");
        } else {
            view.setUrl("classpath:/static/jasper/AllEmployeesByGen.jasper");
        }

        view.setApplicationContext(appContext);

        log.info("datasource is: " + dataSource);
        log.info("appContext is: " + appContext.getApplicationName());
        log.info("gender is: " + ep.getGender().toUpperCase());

        if (ep.getGender().equals("A")) {
            return new ModelAndView(view);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("gender", ep.getGender().toUpperCase());
            return new ModelAndView(view, params);
        }
    }

    @ExceptionHandler()
    @RequestMapping(value = "/excel.xlsx", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcel(@ModelAttribute EmployeePojo ep, Model model) {

        model.addAttribute("ep", ep);

        JasperReportsXlsView view = new JasperReportsXlsView();

        view.setJdbcDataSource(dataSource);

        if (ep.getGender().equals("A")) {
            view.setUrl("classpath:/static/jasper/AllEmployees.jasper");
        } else {
            view.setUrl("classpath:/static/jasper/AllEmployeesByGen.jasper");
        }

        view.setApplicationContext(appContext);

        log.info("datasource is: " + dataSource);
        log.info("appContext is: " + appContext);
        log.info("gender is: " + ep.getGender().toUpperCase());

        if (ep.getGender().equals("A")) {
            return new ModelAndView(view);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("gender", ep.getGender().toUpperCase());
            return new ModelAndView(view, params);
        }
    }

}
