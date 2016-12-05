package com.company;

import zad.one.Constitution;
import zad.one.Set;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("constitution.txt");
        Constitution constitution = new Constitution(path, new Set(5, 31));
    }
}
