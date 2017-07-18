package uk.co.aquaq.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // flags the index() method to support the / route.

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    // Returns "index" as the name of the template
    // Spring Boot’s auto-configured view resolver will map to src/main/resources/templates/index.html.

}
