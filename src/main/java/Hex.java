import java.util.ArrayList;
import java.util.List;


public class Hex {

    private final int id;
    private final List<Edge> edges = new ArrayList();
    private final List<Corner> corners = new ArrayList();

    private int hexValue;
    private ResourceType hexResourceType;
    private boolean hasRobber = false;

    public Hex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return new ArrayList(edges);
    }

    public void initEdges(List<Edge> edges) {
        this.edges.clear();
        this.edges.addAll(edges);
    }

    public List<Corner> getCorners() {
        return new ArrayList<Corner>(corners);
    }

    public void initCorners(List<Corner> corners) {
        this.corners.clear();
        this.corners.addAll(corners);
    }

    public int getHexValue() {
        return hexValue;
    }

    public void setHexValue(int hexValue) {
        this.hexValue = hexValue;
    }

    public ResourceType getHexResourceType() {
        return hexResourceType;
    }

    public void setHexResourceType(ResourceType hexResourceType) {
        this.hexResourceType = hexResourceType;
    }

    public boolean hasRobber() {
        return hasRobber;
    }

    public boolean placeRobber() {
        boolean success = false;

        if(!hasRobber) {
            hasRobber = true;
            success = true;
        }

        return success;
    }

    public void removeRobber() {
        hasRobber = false;
    }

}
