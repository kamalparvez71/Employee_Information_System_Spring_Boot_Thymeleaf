package org.employees.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author opalencia
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "")
    String index() {
        
        return "index";
    }

 
}
