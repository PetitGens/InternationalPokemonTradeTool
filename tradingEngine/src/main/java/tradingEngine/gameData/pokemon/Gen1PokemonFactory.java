package main.java.tradingEngine.gameData.pokemon;

import main.java.tradingEngine.gameData.Language;

public class Gen1PokemonFactory implements PokemonFactory{
    @Override
    public Pokemon newPokemon(byte[] data, byte[] trainerName, byte[] nickname, Language language) {
        return new Gen1Pokemon(data, trainerName, nickname, language);
    }
}
