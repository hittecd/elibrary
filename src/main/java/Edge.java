import java.util.ArrayList;
import java.util.List;


public class Edge {

    private static final int UNOWNED_ID = -1;

    private final int id;
    private final List<Corner> corners = new ArrayList();
    private boolean hasRoad = false;

    private int playerId = UNOWNED_ID;

    public Edge(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void initCorners(List<Corner> corners) {
        this.corners.clear();
        this.corners.addAll(corners);
    }

    public List<Corner> getCorners() {
        return new ArrayList<Corner>(corners);
    }

    public boolean buildRoad(int playerId) {
        // check ownership
        if(this.playerId != UNOWNED_ID || hasRoad)
            return false;

        // check connection to settlement
        for(Corner c : corners) {
            if(c.getPlayerId() == playerId) {
                this.playerId = playerId;
                hasRoad = true;
                return true;
            }
        }

        //check connection to road
        for(Corner c : corners) {
            for(Edge e : c.getEdges()) {
                if(e.getId() != this.id && e.getPlayerId() == playerId) {
                    this.playerId = playerId;
                    hasRoad = true;
                    return true;
                }
            }
        }

        return false;
    }

}
