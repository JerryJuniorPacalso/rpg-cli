package com.settlers.rpg.utils;

import java.io.PrintStream;

public class ConsolePrinter {
    static PrintStream out = System.out;

    public static void setOutput(PrintStream ps) { out = ps; }

    public static void resetOutput(){
        out = System.out;
    }

    public static void info(String message){
        out.println(message);
    }

    public static void title(String message){
        out.println("\n==== "+message.toUpperCase()+" ====");
    }

    public static void prompt(String message){
        out.print(message+" ");
    }

    public static void error(String message){
        out.println("[!] "+message);
    }

    public static void success(String message) {
        out.println("\u001B[32m" + message + "\u001B[0m");
    }

    public static void warn(String message) {
        out.println("\u001B[33m" + message + "\u001B[0m");
    }
}
