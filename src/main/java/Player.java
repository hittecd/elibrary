import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Player {

    private final Map<ResourceType, Integer> resourceCards = new HashMap();
    private final Map<DevelopmentCard, Integer> developmentCards = new HashMap();
    private final List<Edge> edgesList = new ArrayList();
    private final List<Corner> cornersList = new ArrayList();

    private int playerId;
    private int victoryPoints = 0;

    public Player(int playerId) {
        this.playerId = playerId;

        initResourceCards();
        initDevelopmentCards();
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    private void initResourceCards() {
        resourceCards.put(ResourceType.SHEEP, 0);
        resourceCards.put(ResourceType.WHEAT, 0);
        resourceCards.put(ResourceType.ORE, 0);
        resourceCards.put(ResourceType.LUMBER, 0);
        resourceCards.put(ResourceType.BRICK, 0);
    }

    public int getResourceCount(ResourceType resourceType) {
        return resourceCards.get(resourceType);
    }

    public void addResourceCards(Map<ResourceType, Integer> newResourceCards) {

    }

    public void spendResourceCards(Map<ResourceType, Integer> spentResourceCards) {

    }

    private void initDevelopmentCards() {
        developmentCards.put(DevelopmentCard.KNIGHT, 0);
        developmentCards.put(DevelopmentCard.YEAR_OF_PLENTY, 0);
        developmentCards.put(DevelopmentCard.ROAD_BUILDER, 0);
        developmentCards.put(DevelopmentCard.MONOPOLY, 0);
        developmentCards.put(DevelopmentCard.VICTORY_POINT, 0);
    }

    private void playDevelopmentCard(DevelopmentCard devCard) {

    }

    public void addEdge(Edge e) {
        edgesList.add(e);
    }
}
