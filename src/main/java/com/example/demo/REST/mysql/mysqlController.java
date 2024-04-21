package com.example.demo.REST.mysql;

import com.example.demo.util.mySqlRequests;
import com.example.demo.database.mySql.model.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mysql/data")
public class mysqlController {
    @Autowired
    mySqlRequests mySqlRequests;

    @GetMapping("/{id}")
    public data getData(@PathVariable(value = "id") int id){
        return mySqlRequests.getDataById(id);
    }

    @GetMapping
    public Iterable<data> getAllData(){
        return mySqlRequests.getAllData();
    }

    @PostMapping
    public ResponseEntity<data> addData(@RequestBody data data){
        data d = mySqlRequests.addData(data);
        if(d != null)
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.CREATED);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteData(@RequestParam int id) {
        if(mySqlRequests.deleteData(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}