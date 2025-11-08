package com.settlers.rpg.utils;

public class ConsolePrinter {

    public static void info(String message){
        System.out.println(message);
    }

    public static void title(String message){
        System.out.println("\n==== "+message.toUpperCase()+" ====");
    }

    public static void prompt(String message){
        System.out.print(message+" ");
    }

    public static void error(String message){
        System.out.println("[!] "+message);
    }

    public static void success(String message){
        System.out.println("\u001B[32m" + message + "\u001B[0m");
    }

    public static void warn(String message){
        System.out.println("\u001B[33m" + message + "\u001B[0m");
    }
}
