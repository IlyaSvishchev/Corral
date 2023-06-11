package com.example.corral;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorralController {
    @GetMapping("/corral")
    public String corral(Model model){
        return "corral-main";
    }
}
