package com.example.demo.database.mongoDB.service;

import com.example.demo.database.mongoDB.interfaceP.requestRepository;
import com.example.demo.database.mongoDB.model.request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class requestService {
    @Autowired
    requestRepository requestRep;

    public List<request> getAllrequests() {
        return requestRep.findAll();
    }

    public Optional<request> getrequestById(String id) {
        return requestRep.findById(id);
    }

    public request createrequest(request request) {
        return requestRep.save(request);
    }

    public request updaterequest(String id, request request) {
        if (requestRep.existsById(id)) {
            request.setId(id);
            return requestRep.save(request);
        }
        return null;
    }

    public boolean deleterequest(String id) {
        try
        {
            requestRep.deleteById(id);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}