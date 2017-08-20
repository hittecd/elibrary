import java.util.*;


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
        Map<ResourceType, Integer> resourceCardsDeepCopy = new HashMap();
        for(ResourceType resourceType : resourceCards.keySet())
            resourceCardsDeepCopy.put(resourceType, new Integer(resourceCards.get(resourceType)));

        return resourceCardsDeepCopy;
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
        Map<ResourceType, Integer> updatedResourceMap = getResourceCards();

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

    public Map<ResourceType, Integer> stealResourceCard() {
        ArrayList<ResourceType> shuffledHand = new ArrayList();
        for(ResourceType resourceType : resourceCards.keySet()) {
            int cardCount = resourceCards.get(resourceType);
            while(cardCount > 0) {
                shuffledHand.add(resourceType);
                cardCount--;
            }
        }

        if(shuffledHand.size() == 0)
            return null;

        Collections.shuffle(shuffledHand);

        ResourceType stolenCardType = shuffledHand.get(0);

        Map<ResourceType, Integer> stolenCardMap = new HashMap();
        stolenCardMap.put(stolenCardType, 1);
        spendResourceCards(stolenCardMap);

        return stolenCardMap;
    }

    public void playDevelopmentCard(DevelopmentCard developmentCard) {}

    public void addEdge(Edge e) {
        edgesList.add(e);
    }

    public void addCorner(Corner c) {
        cornersList.add(c);
    }

    public void incrementVictoryPoints() {
        victoryPoints++;
    }
}
