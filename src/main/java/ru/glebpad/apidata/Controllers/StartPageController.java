package ru.glebpad.apidata.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String hello(Model model) {
        return "hello.html";
    }

    @PostMapping("/check")
    public String check(@PathParam("request") String request,
                        @PathParam("inns") List<String> inns,
                        Model model) {
        System.out.println(request + " " + inns);
        return null;
    }
}
