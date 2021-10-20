package ru.aGreen.rentalofrealestate.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Images {
    private static List<String> strings = new ArrayList<>();

    private static String image = "";

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
        for (String s : urls) {
            strings.add(s.replace("\"", ""));
        }
        Collections.reverse(strings);
        return strings;
    }

    public static String getImage() {
        return image.replaceAll("\"", "");
    }

    public static void setImage(String image) {
        Images.image = image;
    }
}
