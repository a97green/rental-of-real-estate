package ru.aGreen.rentalofrealestate.models;

import java.util.ArrayList;
import java.util.List;

public class Images {
    private static List<String> strings = new ArrayList<>();

    public static List<String> getStrings() {
        return strings;
    }

    public static void setStrings(List<String> strings) {
        Images.strings = strings;
    }

    public static String generate() {
        StringBuilder urls = new StringBuilder();
        for (String s : Images.getStrings()) {
            urls.append(s).append(";");
        }
        return urls.toString();
    }

    public static List<String> disband(String url) {
        String[] urls = url.split(";");
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            strings.add(urls[i].replace("\"", ""));
        }
        return strings;
    }
}
