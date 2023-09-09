package main.java.tradingEngine;

import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import main.java.tradingEngine.gameData.strings.WesternString;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	byte[] data = {
    			(byte) 0x84,
                (byte) 0x00,
                (byte) 0xD0,
                (byte) 0x2D,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0xC8,
                (byte) 0x22,
                (byte) 0x9C,
                (byte) 0x85,
                (byte) 0x26,
                (byte) 0x4A,
                (byte) 0x84,
                (byte) 0x01,
                (byte) 0xBC,
                (byte) 0xF2,
                (byte) 0x00,
                (byte) 0x75,
                (byte) 0x00,
                (byte) 0x86,
                (byte) 0x00,
                (byte) 0xBB,
                (byte) 0x00,
                (byte) 0x45,
                (byte) 0x00,
                (byte) 0x43,
                (byte) 0x14,
                (byte) 0xFC,
                (byte) 0x0F,
                (byte) 0x0A,
                (byte) 0x14,
                (byte) 0x0F
    	};

        byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

        Pokemon boxPokemon = new Pokemon(data, nickname, nickname, false);
        
        System.out.println(String.format("%x", boxPokemon.getIv_field()));
    }

    public static void inGameStringTesting(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Input hex string : ");

        String hex = sc.nextLine();

        System.out.println(WesternString.hexToString(hex));
        sc.close();
    }
}