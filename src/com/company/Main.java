package com.company;

import zad.one.ArgumentsParser;
import zad.one.Constitution;
import zad.one.Exceptions.ArgumentParserException;
import zad.one.Exceptions.ConstitutionException;
import zad.one.Set;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            ArgumentsParser parser = new ArgumentsParser();
            parser.parse(args);
            System.out.println(parser.getResult());

        }catch(ArgumentParserException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

//        try {
//            Path path = Paths.get("constitution.txt");
//            Constitution constitution = new Constitution(path, new Set(1, 31));
//            System.out.println(constitution.display(5, 37));
//        }catch(ConstitutionException e){
//            System.out.println(e.getMessage());
//            System.exit(1);
//        }
    }
}
