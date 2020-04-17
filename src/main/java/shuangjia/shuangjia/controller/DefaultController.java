package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class DefaultController {
    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }
}
