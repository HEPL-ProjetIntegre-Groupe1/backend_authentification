package com.example.demo.database.control;

import com.example.demo.database.model.data;
import com.example.demo.database.service.dataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/data")
public class dataController {
    @Autowired
    dataService dataService;

    @GetMapping("/data")
    public data getData(@RequestParam(value = "id", defaultValue = "1") int id){
        return dataService.getDataById(id);
    }

    @GetMapping("/allData")
    public Iterable<data> getAllData(){
        return dataService.getAllData();
    }

    @PostMapping("/addData")
    public void addData(@RequestBody data data){
        dataService.addData(data);
    }

}
