package com.example.demo.database.service;

import com.example.demo.database.interfaceP.dataRepository;
import com.example.demo.database.model.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class dataService {
    @Autowired
    dataRepository repository;

    public List<data> getAllData() {
        List<data> data = (List<data>) repository.findAll();
        return data;
    }

    public data getDataById(int id) {
        return repository.findById(id).get();
    }

    public List<data> getDataByUsername(String username) {
        var result = repository.findByUsername(username);
        List<data> dataList = new ArrayList<>();
        result.forEach(dataList::add);
        return dataList;
    }

    public void addData(data data) {
        repository.save(data);
    }
}
