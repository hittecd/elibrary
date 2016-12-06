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

    private void initDevelopmentCards() {
        developmentCards.put(DevelopmentCard.KNIGHT, 0);
        developmentCards.put(DevelopmentCard.YEAR_OF_PLENTY, 0);
        developmentCards.put(DevelopmentCard.ROAD_BUILDER, 0);
        developmentCards.put(DevelopmentCard.MONOPOLY, 0);
        developmentCards.put(DevelopmentCard.VICTORY_POINT, 0);
    }

    public Map<ResourceType, Integer> getResourceCards() {
        return new HashMap<ResourceType, Integer>(resourceCards);
    }

    public Map<DevelopmentCard, Integer> getDevCards() {
        return new HashMap<DevelopmentCard, Integer>(developmentCards);
    }

    public void addResourceCards(Map<ResourceType, Integer> newResourceCards) {
        Integer newCount;
        Integer currentCount;

        for(ResourceType resourceType : newResourceCards.keySet()) {
            newCount = newResourceCards.get(resourceType);
            if(newCount == null)
                newCount = new Integer(0);

            currentCount = resourceCards.get(resourceType);
            if(currentCount == null)
                currentCount = new Integer(0);

            resourceCards.put(resourceType, currentCount + newCount);
        }
    }

    public void addDevelopmentCard(Map<DevelopmentCard, Integer> newDevCards) {

        Integer newCount;
        Integer currentCount;

        for (DevelopmentCard developmentCard : newDevCards.keySet()) {
            newCount = newDevCards.get(developmentCard);
            if (newCount == null)
                newCount = new Integer(0);

            currentCount = developmentCards.get(developmentCard);
            if (currentCount == null)
                currentCount = new Integer(0);

            developmentCards.put(developmentCard, currentCount + newCount);

        }
    }

    public boolean spendResourceCards(Map<ResourceType, Integer> spentResourceCards) {
        Integer currentCount;
        Integer removeCount;
        boolean success = true;
        Map<ResourceType, Integer> updatedResourceMap = new HashMap();

        for(ResourceType resourceType : spentResourceCards.keySet()) {
            currentCount = resourceCards.get(resourceType);
            if(currentCount == null)
                currentCount = new Integer(0);

            removeCount = spentResourceCards.get(resourceType);
            if(removeCount == null)
                removeCount = new Integer(0);

            if((currentCount - removeCount) >= 0)
                updatedResourceMap.put(resourceType, new Integer(currentCount - removeCount));
            else
                success = false;
        }

        if(success) {
            for(ResourceType resourceType : resourceCards.keySet())
                resourceCards.put(resourceType, updatedResourceMap.get(resourceType));
        }

        return success;
    }






    public void playDevelopmentCard(DevelopmentCard developmentCard) {}

    public void addEdge(Edge e) {
        edgesList.add(e);
    }

    public void addCorner(Corner c) {
        cornersList.add(c);
    }

    public void addVictoryPointSettlement(){
        this.victoryPoints ++;
        System.out.println("player " + playerId + " get 1 points and " + getVictoryPoints() + " in total\n" );

    }


    public void addVictoryPointCity(){
        this.victoryPoints ++;
        this.victoryPoints ++;
        System.out.println("player " + playerId + " get 2 points and " + getVictoryPoints() + " in total\n" );

    }
}
