package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.JapaneseString;
import main.java.tradingEngine.gameData.strings.InGameString;
import main.java.tradingEngine.gameData.strings.WesternString;

import java.util.Arrays;
import java.util.Objects;

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
    private final int[] types = new int[2];
    private int catchRate;
    private final int[] moves = new int[4];
    private int trainerId;
    private int exp;
    private final int[] evs = new int[5];

    private int iv_field;
    private final int[] ivs = new int[5];
    private final int[] movesPps = new int[4];
    private final int[] stats = new int[5];
    private InGameString trainerName;
    private InGameString nickname;
    private boolean japanese;
    private byte[] rawData;

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
        this.japanese = japanese;
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

        rawData = new byte[data.length];
        System.arraycopy(data, 0, rawData, 0, rawData.length);

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
        iv_field = Bytes.twoBytesToInt(rawData[0x1B], rawData[0x1C]);

        int iv_copy = iv_field;
        ivs[0] = 0;

        for(int i = 0; i < 4; i++){
            ivs[4 - i] = iv_copy & 0xF;
            ivs[0] += (ivs[4 - i] % 2) << i;
            iv_copy = iv_copy >> 4;
        }

        // Calculate stats if in a box
        if(! inParty){
            calculateStats();
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
    public boolean isJapanese(){return japanese;}

    public byte[] toPartyRawData(){
        if(rawData.length == 44){
            return rawData.clone();
        }

        byte[] returnArray = new byte[44];
        System.arraycopy(rawData, 0, returnArray, 0, 33);

        // The level of a box Pokémon is stored at 0x03 whereas in the party it's stored at 0x21.
        // So we must copy the level at the right location
        returnArray[0x21] = rawData[0x03];

        // The Pokémon's stats are not stored in the box, so we have to put them back

        for(int i = 0; i < 5;i++){
            returnArray[0x22 + 2 * i] = Bytes.getHighByteFrom2BytesValue(stats[i]);
            returnArray[0x23 + 2 * i] = Bytes.getLowByteFrom2BytesValue(stats[i]);
        }

        return returnArray;
    }

    public byte[] toBoxRawData(){
        if(rawData.length == 33){
            return rawData.clone();
        }

        byte[] returnArray = new byte[33];
        System.arraycopy(rawData, 0, returnArray, 0, 33);

        // The level of a box Pokémon is stored at 0x03 whereas in the party it's stored at 0x21.
        // So we must copy the level at the right location
        returnArray[0x03] = rawData[0x21];

        return returnArray;
    }

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

    private Pokemon(){
        specie = Specie.BLANK_SPACE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return indexNumber == pokemon.indexNumber && currentHp == pokemon.currentHp && level == pokemon.level && statusCondition == pokemon.statusCondition && catchRate == pokemon.catchRate && trainerId == pokemon.trainerId && exp == pokemon.exp && japanese == pokemon.japanese && Arrays.equals(types, pokemon.types) && Arrays.equals(moves, pokemon.moves) && Arrays.equals(evs, pokemon.evs) && Arrays.equals(ivs, pokemon.ivs) && Arrays.equals(movesPps, pokemon.movesPps) && Arrays.equals(stats, pokemon.stats) && trainerName.equals(pokemon.trainerName) && nickname.equals(pokemon.nickname);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(indexNumber, currentHp, level, statusCondition, catchRate, trainerId, exp, trainerName, nickname, japanese);
        result = 31 * result + Arrays.hashCode(types);
        result = 31 * result + Arrays.hashCode(moves);
        result = 31 * result + Arrays.hashCode(evs);
        result = 31 * result + Arrays.hashCode(ivs);
        result = 31 * result + Arrays.hashCode(movesPps);
        result = 31 * result + Arrays.hashCode(stats);
        return result;
    }
}
