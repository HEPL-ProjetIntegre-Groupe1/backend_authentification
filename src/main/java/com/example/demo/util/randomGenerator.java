package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class randomGenerator {
    private List<String> images = new ArrayList<String>();
    public randomGenerator() {
        images.add("search");
        images.add("home");
        images.add("menu");
        images.add("close");
        images.add("settings");
        images.add("check_circle");
        images.add("favorite");
        images.add("add");
        images.add("delete");
        images.add("arrow_back");
        images.add("logout");
    }

    public List<String> getRandomImages() {
        Collections.shuffle(images);
        return images.subList(0, 3);
    }
}
