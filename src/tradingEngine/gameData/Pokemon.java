package tradingEngine.gameData;

import tradingEngine.gameData.strings.InGameString;
import tradingEngine.gameData.strings.JapaneseString;
import tradingEngine.gameData.strings.WesternString;

public class Pokemon {
    private Specie specie;
    private int currentHp;
    private int level;
    private int statusCondition;
    private int[] types = new int[2];
    private int catchRate;
    private int[] moves = new int[4];
    private int trainerId;
    private int exp;
    private int[] evs = new int[5];
    private int[] ivs = new int[4];
    private int[] movesPps = new int[4];
    private int[] stats = new int[4];
    private InGameString trainerName;
    private InGameString nickname;

    public Pokemon(int specie, int currentHp, int level, int statusCondition, int[] types, int catchRate, int[] moves, int trainerId, int exp, int[] evs, int[] ivs, int[] movesPps, int[] stats, String trainerName, String nickname) {
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
                throw new SizeLimitExceededException("trainerName is too long");
            }
            if(nicknameLength > 6){
                throw new SizeLimitExceededException("nickname is too long");
            }
        }

        else{
            if(trainerNameLength > 8){
                throw new SizeLimitExceededException("trainerName is too long");
            }
            if(nicknameLength > 11){
                throw new SizeLimitExceededException("nickname is too long");
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

        //TODO parse pokemon data
    }
}
