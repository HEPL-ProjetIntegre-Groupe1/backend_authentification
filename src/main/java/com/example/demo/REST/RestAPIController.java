package com.example.demo.REST;

import com.example.demo.backend.backenLogicClass;
import com.example.demo.database.model.data;
import com.example.demo.database.model.utilisateur;
import com.example.demo.database.service.dataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAPIController {
    @Autowired
    dataService dataService;

    @GetMapping("/data")
    public List<data> getData(@RequestParam(name = "username") String username) {
        return dataService.getDataByUsername("sami");
    }

    @GetMapping("/user")
    public utilisateur getUser(@RequestParam(name = "userId") int userId) {
        utilisateur utilisateur = new utilisateur();
        utilisateur.setId(userId);
        utilisateur.setUsername("admin");
        utilisateur.setPassword("admin");
        return utilisateur;
    }
}
