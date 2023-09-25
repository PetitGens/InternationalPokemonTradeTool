package main.java.tradingEngine;

import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveBackups;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;

import java.io.IOException;
import java.util.Scanner;

/**
 * Application class that runs a command-line-interface.
 * The latter allows the user to trade between two save files, by first inputting the save files' paths, and then
 * inputting the two Pokémon's positions.
 * @author Julien Ait azzouzene
 */
public class CLIApp {

    private static Scanner scanner;

    /**
     * The application's main class.
     */
    public static void main(String[] args){
        scanner = new Scanner(System.in);

        SaveBackups.deleteOldBackups();

        SaveFile saveFile1 = saveFilePathInput(1);
        SaveFile saveFile2 = saveFilePathInput(2);

        Trade trade = new Trade(saveFile1, saveFile2);

        while(true){
            int[] pokemon1Position = inputPokemonPosition(1, saveFile1);
            int[] pokemon2Position = inputPokemonPosition(2, saveFile2);

            boolean pokemon1InParty = pokemon1Position[1] < 0;
            boolean pokemon2InParty = pokemon2Position[1] < 0;

            try{
                trade.trade(pokemon1InParty, pokemon1Position[0], pokemon1Position[1],
                        pokemon2InParty, pokemon2Position[0], pokemon2Position[1]);

                System.out.println("The trade was successful !");
            }
            catch (IOException ioException){
                System.out.println("The trade failed (maybe you haven't write permission with the save files).");
            }
        }
    }

    /**
     * Asks the user for the save file's path, and then tries to open and return it.
     * @param i -> the save file's number, just for display (either 1 or 2)
     * @return -> the just opened save file
     */
    private static SaveFile saveFilePathInput(int i){
        SaveFile saveFile = null;
        do {
            System.out.printf("Please input the path of save file %d : ", i);
            String path = scanner.nextLine();
            try{
                saveFile = new SaveFile(path);
            }
            catch (IOException ioException){
                System.out.println("Invalid save file !\nReason : " + ioException.getMessage());
            }
        } while(saveFile == null);

        return saveFile;
    }

    /**
     * Asks the user for the position of a Pokémon (either one number if it's in the party, or two if it's in a box).
     * @param saveIndex -> the save file's number, just for display (either 1 or 2)
     * @param saveFile -> the save file the Pokémon is located on
     * @return -> an array of two integers : the first one is the Pokémon index, the second one is the box number or a
     * negative number if the Pokémon is in the party
     */
    private static int[] inputPokemonPosition(int saveIndex, SaveFile saveFile){
        int[] position = new int[2];

        boolean validInput = false;
        while(! validInput){
            System.out.printf("Which Pokémon do you want to trade from save %d ? ", saveIndex);
            System.out.print("(\"[position in party]\" or \"[position in box] [box number]\") : ");

            String line = scanner.nextLine();

            String[] splitLine = line.split(" ");

            try{
                int length = splitLine.length;
                if(length < 1 || length > 2){
                    throw new Exception("invalid number of fields : " + length);
                }

                position[0] = Integer.parseInt(splitLine[0]);
                position[1] = length == 1 ? -1 : Integer.parseInt(splitLine[1]);

                position[0]--;
                position[1]--;

                Pokemon pokemon = length == 1 ?
                        saveFile.getPartyPokemon(position[0])
                        : saveFile.getBoxPokemon(position[1], position[0]);
                if (pokemon.getSpecie() == Specie.BLANK_SPACE){
                    throw new Exception("there is no Pokémon at this position (save " + saveIndex + ")");
                }
                validInput = true;
            }
            catch (Exception exception){
                System.out.println("Invalid input !");
                System.out.println(exception.getMessage());
            }
        }
        return position;
    }
}
