import tradingEngine.gameData.strings.WesternString;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char truc = 'あ';
        System.out.println(truc);
    }

    public static void inGameStringTesting(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Input hex string : ");

        String hex = sc.nextLine();

        System.out.println(WesternString.hexToString(hex));
        sc.close();
    }
}