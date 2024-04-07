package com.example.demo.REST;

import com.example.demo.backend.backenLogicClass;
import com.example.demo.database.mongoDB.model.request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mongoDB/request")
public class requestController {
    @Autowired
    private backenLogicClass backenLogicClass;

    @GetMapping("/getAll")
    public List<request> getAllrequests() {
        return backenLogicClass.getAllrequests();
    }

    @GetMapping("/get")
    public request getrequestById(@RequestParam String id) {
        Optional<request> request = backenLogicClass.getrequestById(id);
        return request.orElse(null);
    }

    @PostMapping
    public ResponseEntity<request> createrequest(@RequestBody request request) {
        request newrequest = backenLogicClass.createrequest(request);
        return new ResponseEntity<>(newrequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<request> updaterequest(@RequestParam String id, @RequestBody request request) {
        request updatedrequest = backenLogicClass.updaterequest(id, request);
        if (updatedrequest != null) {
            return new ResponseEntity<>(updatedrequest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleterequest(@RequestParam String id) {
        if(backenLogicClass.getrequestById(id).isPresent()) {
            backenLogicClass.deleterequest(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
