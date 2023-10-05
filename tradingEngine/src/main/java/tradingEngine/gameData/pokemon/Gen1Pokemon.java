package main.java.tradingEngine.gameData.pokemon;

import main.java.tradingEngine.gameData.Bytes;
import main.java.tradingEngine.gameData.Language;

public class Gen1Pokemon extends Pokemon{
    public static Pokemon BLANK_SPACE = new Gen1Pokemon();

    /**
     * The main constructor that basically takes raw data and interpret them as a Pokémon. The main data section must
     * either be 44 bytes (if in party) or 33 bytes long (if in a box).
     *
     * @param data        an array containing every single byte in a Pokémon's main data
     * @param trainerName the original trainer name as an encoded in-game string
     * @param nickname    the Pokémon's nickname as an encoded in-game string
     * @param language    the Pokémon's language (Japanese or Western)
     */
    public Gen1Pokemon(byte[] data, byte[] trainerName, byte[] nickname, Language language) {
        super(data, trainerName, nickname, language);
        parsePokemonData();
    }

    /**
     * A method used in the constructor to put all the Pokémon's data at the right variable
     */
    private void parsePokemonData(){
        boolean inParty = false;

        if(rawData.length == 44){
            inParty = true;
        } else if (rawData.length != 33) {
            throw new IllegalArgumentException("Pokémon data should either be 44 bytes long for a party Pokémon or 33" +
                    "bytes long for a box Pokémon");
        }

        // Pokémon Specie
        indexNumber = Bytes.byteToUnsignedByte(rawData[0x0]);
        specie = Specie.specieFromGen1Index(indexNumber);

        currentHp = Bytes.twoBytesToInt(rawData[0x1], rawData[0x2]);

        // Level
        if(inParty){
            level = Bytes.byteToUnsignedByte(rawData[0x21]);
        }
        else{
            level = Bytes.byteToUnsignedByte(rawData[0x3]);
        }

        statusCondition = Bytes.byteToUnsignedByte(rawData[0x4]);

        // Pokémon double type
        types[0] = Bytes.byteToUnsignedByte(rawData[0x5]);
        types[1] = Bytes.byteToUnsignedByte(rawData[0x6]);

        catchRate = Bytes.byteToUnsignedByte(rawData[0x7]);

        // Moves and PPs
        for(int i = 0; i < 4; i++){
            moves[i] = Bytes.byteToUnsignedByte(rawData[0x8 + i]);
            movesPps[i] = Bytes.byteToUnsignedByte(rawData[0x1D + i]);
        }

        trainerId = Bytes.twoBytesToInt(rawData[0xc], rawData[0xd]);

        exp = Bytes.threeBytesToInt(rawData[0xe], rawData[0xf], rawData[0x10]);

        // Effort Points (and stats if the Pokémon is in party)
        for (int i = 0; i < 5; i++){
            evs[i] = Bytes.twoBytesToInt(rawData[0x11 + i * 2], rawData[0x12 + i * 2]);

            if(inParty){
                stats[i] = Bytes.twoBytesToInt(rawData[0x22 + i * 2], rawData[0x23 + i * 2]);
            }
        }

        // IVs
        int ivField = Bytes.twoBytesToInt(rawData[0x1B], rawData[0x1C]);
        ivs[0] = 0;

        for(int i = 0; i < 4; i++){
            ivs[4 - i] = ivField & 0xF;
            ivs[0] += (ivs[4 - i] % 2) << i;
            ivField = ivField >> 4;
        }

        // Calculate stats if in a box
        if(! inParty){
            calculateStats();
        }
    }

    /**
     * Calculate the Pokémon's stats based on it's base stats (specie-dependants), IVs and EVs.
     * This is used for Pokémon stored in a box since the game does not store the stats in this case, they are
     * calculated when the Pokémon is withdrawn.
     */
    private void calculateStats(){
        // Get base stats
        int[] baseStats = new int[5];
        baseStats[0] = specie.getBaseHP();
        baseStats[1] = specie.getBaseAttack();
        baseStats[2] = specie.getBaseDefense();
        baseStats[3] = specie.getBaseSpeed();
        baseStats[4] = specie.getBaseSpecial();

        // HP
        // floor((((BaseStat + IV) × 2 + floor(ceil(sqrt(EV)) ÷ 4)) × Lvl) ÷ 100) + Lvl + 10.
        stats[0] = ((baseStats[0] + ivs[0]) * 2 + (int) Math.ceil(Math.sqrt(evs[0])) / 4) * level / 100 + level + 10;

        // Attack, Defense, Speed and Special
        // floor((((BaseStat + IV) × 2 + floor(ceil(sqrt(EV)) ÷ 4)) × Lvl) ÷ 100) + 5
        for (int i = 1; i < 5; i++){
            stats[i] = ((baseStats[i] + ivs[i]) * 2 + (int) Math.ceil(Math.sqrt(evs[i])) / 4) * level / 100 + 5;
        }
    }

    /**
     * Just the constructor for the special Pokémon BLANK_SPACE
     */
    private Gen1Pokemon(){
        super();
    }
}
