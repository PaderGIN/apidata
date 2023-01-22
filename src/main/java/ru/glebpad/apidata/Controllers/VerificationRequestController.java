package ru.glebpad.apidata.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VerificationRequestController {
    @GetMapping("/request")
    public String request(Model model){
        return "redirect:request";
    }
}
