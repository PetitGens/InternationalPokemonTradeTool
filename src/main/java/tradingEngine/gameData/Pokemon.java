package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.JapaneseString;
import main.java.tradingEngine.gameData.strings.InGameString;
import main.java.tradingEngine.gameData.strings.WesternString;

public class Pokemon {
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

    public Pokemon(int specie, int currentHp, int level, int statusCondition, int[] types, int catchRate, int[] moves, int trainerId, int exp, int[] evs, int[] ivs, int[] movesPps, int[] stats) {
        this.specie = Specie.specieFromIndex(specie);
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

    public Pokemon(byte[] data, byte[] trainerName, byte[] nickname, boolean japanese) {
        int trainerNameLength = InGameString.stringLength(trainerName);
        int nicknameLength = InGameString.stringLength(nickname);

        if(japanese){
            if(trainerNameLength > 6){
                throw new IllegalArgumentException("trainerName is too long");
            }
            if(nicknameLength > 6){
                throw new IllegalArgumentException("nickname is too long");
            }
        }

        else{
            if(trainerNameLength > 8){
                throw new IllegalArgumentException("trainerName is too long");
            }
            if(nicknameLength > 11){
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
        specie = Specie.specieFromIndex(indexNumber);

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

    public Specie getSpecie() {
        return specie;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public int getStatusCondition() {
        return statusCondition;
    }

    public int[] getTypes() {
        return types;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public int[] getMoves() {
        return moves;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public int getExp() {
        return exp;
    }

    public int[] getEvs() {
        return evs;
    }

    public int getIv_field() {
        return iv_field;
    }

    public int[] getIvs() {
        return ivs;
    }

    public int[] getMovesPps() {
        return movesPps;
    }

    public int[] getStats() {
        return stats;
    }

    public InGameString getTrainerName() {
        return trainerName;
    }

    public InGameString getNickname() {
        return nickname;
    }
}
