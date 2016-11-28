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

    public int getPlayerId(int playerId) {
        return playerId;
    }

    public void initCorners(List<Corner> corners) {
        this.corners.clear();
        this.corners.addAll(corners);
    }

    public boolean buildRoad(int playerId) {
        boolean success = false;

        if(this.playerId == UNOWNED_ID && !hasRoad) {
            this.playerId = playerId;
            hasRoad = true;
        }

        return success;
    }

}
