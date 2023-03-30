package fr.ses10doigts.webApp2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/front/test")
public class TestFrontController {

    private static final Logger logger = LoggerFactory.getLogger(TestFrontController.class);

    @GetMapping("/bs")
    public String testBootStrap(Model model) {
	logger.info("Sending Bootstrap template");
	return "bootstrap";
    }


}
