package main.java.tradingEngine;
import main.java.tradingEngine.gameData.strings.WesternString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
    	fileIOTest();
    }

    public static void inGameStringTesting(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Input hex string : ");

        String hex = sc.nextLine();

        System.out.println(WesternString.hexToString(hex));
        sc.close();
    }

    public static void fileIOTest() throws IOException {
        long before = System.currentTimeMillis();
        byte[] data = Files.readAllBytes(Path.of("src/main/resources/tradingEngine/gameData/save.sav"));
        int i = 0;
        for(byte truc : data){
            i++;
        }
        long after = System.currentTimeMillis();
        System.out.println("Time : "+(int) (after-before));
        System.out.println("i="+i);
    }
}