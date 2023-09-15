package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.JapaneseString;
import main.java.tradingEngine.gameData.strings.InGameString;
import main.java.tradingEngine.gameData.strings.WesternString;

/**
 * This class is meant to store a Pokémon's data.
 * @author Julien Ait azzouzene
 */
public class Pokemon {
    public static final Pokemon BLANK_SPACE = new Pokemon();

    private Specie specie;
    private int indexNumber;
    private int currentHp;
    private int level;
    private int statusCondition;
    private int[] types = new int[2];
    private int catchRate;
    private int[] moves = new int[4];
    private int trainerId;
    private int exp;
    private int[] evs = new int[5];

    private int iv_field;
    private int[] ivs = new int[5];
    private int[] movesPps = new int[4];
    private int[] stats = new int[5];
    private InGameString trainerName;
    private InGameString nickname;

    /**
     * This creates a Pokémon by specifying every field. I'm not sure if it'll end up being used in other means than
     * testing.
     * @param specie -> the Pokémon's specie internal index number
     * @param currentHp -> the Pokémon's current HP number
     * @param level -> the Pokémon's level
     * @param statusCondition -> the Pokémon's current condition (0 if it has none)
     * @param types -> the Pokémon's double type
     * @param catchRate -> the Pokémon's catch rate (becomes held item when send in Gen2)
     * @param moves -> the Pokémon's movement
     * @param trainerId -> the Pokémon's original trainer's id number
     * @param exp -> the Pokémon's current experience points number
     * @param evs -> the Pokémon's current effort values
     * @param ivs -> the Pokémon's current individual values (also called DVs in gen1/2)
     * @param movesPps -> the Pokémon's pps value (each value contains the remaining pp of the move and the number of
     * used pp ups)
     * @param stats -> the Pokémon's stats
     */
    public Pokemon(int specie, int currentHp, int level, int statusCondition, int[] types, int catchRate, int[] moves, int trainerId, int exp, int[] evs, int[] ivs, int[] movesPps, int[] stats) {
        this.specie = Specie.specieFromGen1Index(specie);
        this.currentHp = currentHp;
        this.level = level;
        this.statusCondition = statusCondition;
        this.types = types;
        this.catchRate = catchRate;
        this.moves = moves;
        this.trainerId = trainerId;
        this.exp = exp;
        this.evs = evs;
        this.ivs = ivs;
        this.movesPps = movesPps;
        this.stats = stats;
    }

    /**
     * The main constructor that basically takes raw data and interpret them as a Pokémon. The main data section must
     * either be 44 bytes (if in party) or 33 bytes long (if in a box).
     * @param data -> an array containing every single byte in a Pokémon's main data
     * @param trainerName -> the original trainer name as an encoded in-game string
     * @param nickname -> the Pokémon's nickname as an encoded in-game string
     * @param japanese -> whether the Pokémon's is stored on a Japanese game
     * @see InGameString
     */
    public Pokemon(byte[] data, byte[] trainerName, byte[] nickname, boolean japanese) {
        int trainerNameLength = InGameString.stringLength(trainerName);
        int nicknameLength = InGameString.stringLength(nickname);

        if(japanese){
            if(trainerNameLength > 5){
                throw new IllegalArgumentException("trainerName is too long");
            }
            if(nicknameLength > 5){
                throw new IllegalArgumentException("nickname is too long");
            }
        }

        else{
            if(trainerNameLength > 7){
                throw new IllegalArgumentException("trainerName is too long");
            }
            if(nicknameLength > 10){
                throw new IllegalArgumentException("nickname is too long");
            }
        }

        // Beyond this point, nickname and trainer name have normal sizes

        if(japanese){
            this.trainerName = new JapaneseString();
            this.nickname = new JapaneseString();
        }
        else{
            this.trainerName = new WesternString();
            this.nickname = new WesternString();
        }

        try{
            this.trainerName.addAll(trainerName);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("illegal character in trainerName");
        }

        try{
            this.nickname.addAll(nickname);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("illegal character in nickname");
        }

        parsePokemonDate(data);
    }

    /**
     * A method used in the constructor to put all the Pokémon's data at the right variable
     * @param data -> an array containing every single byte in a Pokémon's main data
     */
    private void parsePokemonDate(byte[] data){
        boolean inParty = false;

        if(data.length == 44){
            inParty = true;
        } else if (data.length != 33) {
            throw new IllegalArgumentException("Pokémon data should either be 44 bytes long for a party Pokémon or 33" +
                    "bytes long for a box Pokémon");
        }

        // Pokémon Specie
        indexNumber = Bytes.byteToUnsignedByte(data[0x0]);
        specie = Specie.specieFromGen1Index(indexNumber);

        currentHp = Bytes.twoBytesToInt(data[0x1], data[0x2]);

        // Level
        if(inParty){
            level = Bytes.byteToUnsignedByte(data[0x21]);
        }
        else{
            level = Bytes.byteToUnsignedByte(data[0x3]);
        }

        statusCondition = Bytes.byteToUnsignedByte(data[0x4]);

        // Pokémon double type
        types[0] = Bytes.byteToUnsignedByte(data[0x5]);
        types[1] = Bytes.byteToUnsignedByte(data[0x6]);

        catchRate = Bytes.byteToUnsignedByte(data[0x7]);

        // Moves and PPs
        for(int i = 0; i < 4; i++){
            moves[i] = Bytes.byteToUnsignedByte(data[0x8 + i]);
            movesPps[i] = Bytes.byteToUnsignedByte(data[0x1D + i]);
        }

        trainerId = Bytes.twoBytesToInt(data[0xc], data[0xd]);

        exp = Bytes.threeBytesToInt(data[0xe], data[0xf], data[0x10]);

        // Effort Points (and stats if the Pokémon is in party)
        for (int i = 0; i < 5; i++){
            evs[i] = Bytes.twoBytesToInt(data[0x11 + i * 2], data[0x12 + i * 2]);

            if(inParty){
                stats[i] = Bytes.twoBytesToInt(data[0x22 + i * 2], data[0x23 + i * 2]);
            }
        }

        // IVs
        iv_field = Bytes.twoBytesToInt(data[0x1B], data[0x1C]);

        int iv_copy = iv_field;
        ivs[0] = 0;

        for(int i = 0; i < 4; i++){
            ivs[4 - i] = iv_copy & 0xF;
            ivs[0] += (ivs[4 - i] % 2) << i;
            iv_copy = iv_copy >> 4;
        }

    }

    /**
     * Returns the Specie of the Pokémon.
     * @return the Pokémon's specie
     * @see Specie
     */
    public Specie getSpecie() {
        return specie;
    }

    /**
     * Returns the internal index number of the Pokémon's specie.
     * @return the Pokémon's specie's internal index number.
     */
    public int getIndexNumber() {
        return indexNumber;
    }

    /**
     * Returns the Pokémon's current HP value.
     * @return the Pokémon's current HP
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Returns the Pokémon's current level.
     * @return the Pokémon's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the Pokémon's current condition (0 if it has none).
     * @return the Pokémon's status condition
     */
    public int getStatusCondition() {
        return statusCondition;
    }

    /**
     * Returns the Pokémon's double type.
     * @return the Pokémon's double type
     */
    public int[] getTypes() {
        return types;
    }

    /**
     * Returns the Pokémon's catch rate (if in Gen1) or held item's internal index number (if in Gen2).
     * @return the Pokémon's catch rate
     */
    public int getCatchRate() {
        return catchRate;
    }

    /**
     * Returns every move's internal index number.
     * @return the moves in an array
     */
    public int[] getMoves() {
        return moves.clone();
    }

    /**
     * Returns the Pokémon's original trainer's id number.
     * @return the trainerID
     */
    public int getTrainerId() {
        return trainerId;
    }

    /**
     * Returns the Pokémon's current experience points number.
     * @return the exp points number
     */
    public int getExp() {
        return exp;
    }

    /**
     * Returns an array containing every the effort value of every stat in the following order : HP, ATTACK, DEFENSE,
     * SPEED, SPECIAL (in Gen2, Special Attack and Special Defense share the same EV and IV).
     * @return the EVs.
     */
    public int[] getEvs() {
        return evs.clone();
    }

    /**
     * Returns the raw IV field (meaning all the IVs put together in a 16 bits number).
     * @return the IV field.
     */
    public int getIv_field() {
        return iv_field;
    }

    /**
     * Returns an array containing every the individual value of every stat in the following order : HP, ATTACK, DEFENSE,
     * SPEED, SPECIAL (in Gen2, Special Attack and Special Defense share the same EV and IV).
     * @return the IVs.
     */
    public int[] getIvs() {
        return ivs.clone();
    }

    /**
     * Returns the Pokémon's pps value (each value contains the remaining pp of the move and the number of
     * used pp ups).
     * @return the moves' pp values
     */
    public int[] getMovesPps() {
        return movesPps.clone();
    }

    /**
     * Returns the Pokémon's 6 stats.
     * @return the Pokémon's stats
     */
    public int[] getStats() {
        return stats.clone();
    }

    /**
     * Returns the Pokémon's original trainer's name.
     * @return the trainer's name
     * @see InGameString
     */
    public InGameString getTrainerName() {
        return trainerName;
    }

    /**
     * Returns the Pokémon nickname.
     * @return the Pokémon nickname
     */
    public InGameString getNickname() {
        return nickname;
    }

    private Pokemon(){
        specie = Specie.BLANK_SPACE;
    }
}
