package tradingEngine.gameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class InGameString extends ArrayList<byte> {

    private static final int MAX_SIZE = 8;

    @Override
    public boolean add(byte b) {
        if(size() >= MAX_SIZE){
            throw new SizeLimitExceededException();
        }

        return super.add(b);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends byte> c) {
        if(size() + c.size() > MAX_SIZE){
            throw new SizeLimitExceededException();
        }

        return super.addAll(c);
    }

    public InGameString(Collection<? extends byte> c) {
        super(c);
        if(c.size() > MAX_SIZE){
            throw new SizeLimitExceededException();
        }
    }

    @Override
    public String toString() {
        //TODO convert to Java String
        return null;
    }
}
