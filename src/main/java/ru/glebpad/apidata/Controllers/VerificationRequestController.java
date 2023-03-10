package ru.glebpad.apidata.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.glebpad.apidata.Services.SearchService;

import java.sql.SQLException;

@RequestMapping("/request")
@Controller
public class VerificationRequestController {
    private final SearchService searchService;


    @Autowired
    public VerificationRequestController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("")
    public String request(Model model) {
        return "request";
    }

    @GetMapping("/search")
    public String analyseRequest() throws SQLException {
        searchService.handleRequest();
        return "result";
    }
}
