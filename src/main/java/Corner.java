import java.util.ArrayList;
import java.util.List;


public class Corner {

    private static final int UNOWNED_ID = -1;

    private final int id;
    private final List<Edge> edges = new ArrayList();

    private int playerId = UNOWNED_ID;
    private boolean hasSettlement = false;
    private boolean hasCity = false;

    public Corner(int id) {
        this.id = id;
    }

    public int getPlayerId(int playerId) {
        return playerId;
    }

    public void initEdges(List<Edge> edges) {
        this.edges.clear();
        this.edges.addAll(edges);
    }

    public boolean buildSettlement(int playerId) {
        boolean success = false;

        if(this.playerId == UNOWNED_ID && !hasSettlement) {
            this.playerId = playerId;
            hasSettlement = true;
            success = true;
        }

        return success;
    }

    public boolean hasSettlement() {
        return hasSettlement;
    }

    private boolean buildCity(int playerId) {
        boolean success = false;

        if(this.playerId == playerId && hasSettlement) {
            hasCity = true;
            success = true;
        }

        return success;
    }

    public boolean hasCity() {
        return hasCity;
    }

}
