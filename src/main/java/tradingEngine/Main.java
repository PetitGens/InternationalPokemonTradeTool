package main.java.tradingEngine;

import main.java.tradingEngine.gameData.strings.WesternString;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        byte truc = (byte) 0x80;

        int truc2 = truc;

        System.out.println(truc);
        System.out.println(truc2);
    }

    public static void inGameStringTesting(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Input hex string : ");

        String hex = sc.nextLine();

        System.out.println(WesternString.hexToString(hex));
        sc.close();
    }
}