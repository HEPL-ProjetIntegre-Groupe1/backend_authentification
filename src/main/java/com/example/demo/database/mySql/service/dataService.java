package com.example.demo.database.mySql.service;

import com.example.demo.database.mySql.interfaceP.dataRepository;
import com.example.demo.database.mySql.model.data;
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

    public data addData(data data) {
        try {
            repository.save(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteData(int id) {
        repository.deleteById(id);
    }
}
