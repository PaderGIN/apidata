package ru.glebpad.apidata.Controllers;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String hello(Model model) {
        return "hello";
    }

    @PostMapping("/check")
    public String check(@ModelAttribute String request) {
        System.out.println((request));

        return null;
    }
}
