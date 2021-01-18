package jpashop.controller;
import io.swagger.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

	@ApiOperation(value="home")
    @RequestMapping(value ="/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
