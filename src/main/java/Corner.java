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

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void initEdges(List<Edge> edges) {
        this.edges.clear();
        this.edges.addAll(edges);
    }

    public List<Edge> getEdges() {
        return new ArrayList(edges);
    }

    public boolean buildSettlement(int playerId, boolean inSetupPhase) {

        // check ownership
        if(this.playerId != UNOWNED_ID || hasSettlement)
            return false;

        // check connected to road if not currently in setup phase
        if(!inSetupPhase) {
            boolean onRoad = false;

            for (Edge e : edges) {
                if(e.getPlayerId() == playerId) {
                    onRoad = true;
                    break;
                }
            }

            if(!onRoad)
                return false;
        }

        // check distance from other cities
        for(Edge e : edges) {
            for(Corner c : e.getCorners()) {
                if(c.getId() != this.id && c.getPlayerId() != UNOWNED_ID)
                    return false;
            }
        }

        this.playerId = playerId;
        hasSettlement = true;

        return true;
    }

    public boolean hasSettlement() {
        return hasSettlement;
    }

    public boolean buildCity(int playerId) {
        if(this.playerId == playerId && hasSettlement()) {
            hasCity = true;
            hasSettlement = false;
            return true;
        }

        return false;
    }

    public boolean hasCity() {
        return hasCity;
    }

}
