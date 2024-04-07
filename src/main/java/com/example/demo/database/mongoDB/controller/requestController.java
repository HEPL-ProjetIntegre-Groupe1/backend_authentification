package com.example.demo.database.mongoDB.controller;

import com.example.demo.database.mongoDB.model.request;
import com.example.demo.database.mongoDB.service.requestService;
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
    private requestService requestS;

    @GetMapping("/getAll")
    public List<request> getAllrequests() {
        return requestS.getAllrequests();
    }

    @GetMapping("/get")
    public request getrequestById(@RequestParam String id) {
        Optional<request> request = requestS.getrequestById(id);
        return request.orElse(null);
    }

    @PostMapping
    public ResponseEntity<request> createrequest(@RequestBody request request) {
        request newrequest = requestS.createrequest(request);
        return new ResponseEntity<>(newrequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<request> updaterequest(@RequestParam String id, @RequestBody request request) {
        request updatedrequest = requestS.updaterequest(id, request);
        if (updatedrequest != null) {
            return new ResponseEntity<>(updatedrequest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleterequest(@RequestParam String id) {
        if(requestS.getrequestById(id).isPresent()) {
            requestS.deleterequest(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
