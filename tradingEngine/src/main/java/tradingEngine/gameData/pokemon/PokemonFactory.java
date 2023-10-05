package main.java.tradingEngine.gameData.pokemon;

import main.java.tradingEngine.gameData.Language;

public interface PokemonFactory {
    public abstract Pokemon newPokemon(byte[] data, byte[] trainerName, byte[] nickname, Language language);
}
