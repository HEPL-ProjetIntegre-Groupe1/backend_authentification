package com.example.demo.web;

import com.example.demo.backend.backenLogicClass;
import com.example.demo.database.mySql.model.data;
import com.example.demo.database.mySql.model.utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class webController {
    @Autowired
    private backenLogicClass backenLogicClass;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping( "/login")
    public String login(Model model, @RequestParam(name = "error", defaultValue = "false") boolean error) {
        model.addAttribute("error", error);
        model.addAttribute("utilisateur", new utilisateur());
        return "formular";
    }

    @PostMapping("/verifyLogin")
    public String verifyLogin(@ModelAttribute utilisateur utilisateur) {
        if(backenLogicClass.verifyLogin(utilisateur.getUsername(), utilisateur.getPassword())) {
            return "redirect:/inputData?userId=%d".formatted(utilisateur.getId());
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/inputData")
    public String inputData(Model model, @RequestParam(name = "userId") int userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("data", new data());
        model.addAttribute("utilisateur", new utilisateur());
        return "inputData";
    }

    @PostMapping("/saveData")
    public String saveData(@ModelAttribute data data) {
        if(backenLogicClass.saveData(data)) {
            return "redirect:/feelsgoodman?sentence=%s".formatted(data.getNotSoSecretData());
        }
        return "redirect:/sadge";
    }

    @GetMapping("/feelsgoodman")
    public String feelsgoodman(Model model, @RequestParam(name = "sentence") String sentence) {
        model.addAttribute("sentence", sentence);
        return "feelsgoodman";
    }

    @GetMapping("/sadge")
    public String sadge() {
        return "sadge";
    }
}
