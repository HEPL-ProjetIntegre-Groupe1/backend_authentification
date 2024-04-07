package com.example.demo.database.mySql.control;

import com.example.demo.database.mySql.model.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mysql/data")
public class dataController {
    @Autowired
    com.example.demo.database.mySql.service.dataService dataService;

    @GetMapping("/get")
    public data getData(@RequestParam(value = "id", defaultValue = "1") int id){
        return dataService.getDataById(id);
    }

    @GetMapping("/getAll")
    public Iterable<data> getAllData(){
        return dataService.getAllData();
    }

    @PostMapping
    public ResponseEntity<data> addData(@RequestBody data data){
        data d = dataService.addData(data);
        if(d != null)
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.CREATED);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteData(@RequestParam int id) {
        if(dataService.getDataById(id) != null) {
            dataService.deleteData(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
