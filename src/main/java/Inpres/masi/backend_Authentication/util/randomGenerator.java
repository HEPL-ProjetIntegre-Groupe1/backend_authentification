package Inpres.masi.backend_Authentication.util;

import org.springframework.stereotype.Service;

import java.util.*;

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

    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    public String generateRandomCode() {
        Random random = new Random();
        // minimum is 100000
        // maximum is 999999
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
