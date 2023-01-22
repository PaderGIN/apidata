package ru.glebpad.apidata.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String hello(Model model) {
        return "main";
    }
}
