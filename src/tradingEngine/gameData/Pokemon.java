package tradingEngine.gameData;

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

    public Pokemon(byte[] data, byte[] trainerName, byte[] nickname, boolean japanese){
        //Parse trainer name and nickname

        //TODO parse pokemon data
    }
}
