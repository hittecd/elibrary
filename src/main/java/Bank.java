import java.util.*;


public class Bank {

    private static final int KNIGHT_CARD_COUNT = 14;
    private static final int ROAD_BUILDER_COUNT = 2;
    private static final int MONOPOLY_COUNT = 2;
    private static final int YEAR_OF_PLENTY_COUNT = 2;
    private static final int VICTORY_POINT_COUNT = 5;
    private static final int TOTAL_RESOURCE_COUNT = 19;

    private class DevelopmentCardQueue {

        private class Node {
            private Node prev;
            private Node next;
            private DevelopmentCard data;

            public Node(Node prev, DevelopmentCard data, Node next) {
                this.prev = prev;
                this.data = data;
                this.next = next;
            }

            public Node getPrev() {
                return prev;
            }

            public void setPrev(Node n) {
                prev = n;
            }

            public DevelopmentCard getData() {
                return data;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node n) {
                next = n;
            }
        }

        private Node head;
        private Node tail;

        public void addAll(Collection<DevelopmentCard> devCardCollection) {
            for(DevelopmentCard newCard : devCardCollection) {
                push(newCard);
            }
        }

        public DevelopmentCard pop() {
            if(head == null)
                return null;

            DevelopmentCard result = head.getData();

            head = head.getNext();
            if(head != null) {
                head.prev = null;
            }

            return result;
        }

        public void push(DevelopmentCard newCard) {
            Node newNode = new Node(null, newCard, null);

            if(head == null) {
                head = newNode;
                tail = newNode;
            }
            else {
                tail.setNext(newNode);
                newNode.setPrev(tail);
                tail = newNode;
            }
        }
    }

    private final Map<ResourceType, Integer> resourceMap = new HashMap();
    private final DevelopmentCardQueue developmentCardQueue = new DevelopmentCardQueue();

    public Bank() {
        initResourceTable();
        initDevelopmentCardQueue();
    }

    private void initResourceTable() {
        // initialize 19 of each Resource Type
        resourceMap.put(ResourceType.LUMBER, TOTAL_RESOURCE_COUNT);
        resourceMap.put(ResourceType.WHEAT, TOTAL_RESOURCE_COUNT);
        resourceMap.put(ResourceType.SHEEP, TOTAL_RESOURCE_COUNT);
        resourceMap.put(ResourceType.BRICK, TOTAL_RESOURCE_COUNT);
        resourceMap.put(ResourceType.ORE, TOTAL_RESOURCE_COUNT);
    }

    private void initDevelopmentCardQueue() {
        List<DevelopmentCard> devCards = new ArrayList();

        // add 14 'KNIGHT' cards
        for(int i=1; i<=KNIGHT_CARD_COUNT; i++)
            devCards.add(DevelopmentCard.KNIGHT);

        // add 5 'VICTORY POINT' cards
        for(int i=1; i<=VICTORY_POINT_COUNT; i++)
            devCards.add(DevelopmentCard.VICTORY_POINT);

        // add 2 'MONOPOLY' cards
        devCards.add(DevelopmentCard.MONOPOLY);
        devCards.add(DevelopmentCard.MONOPOLY);

        // add 2 'YEAR OF PLENTY' cards
        devCards.add(DevelopmentCard.YEAR_OF_PLENTY);
        devCards.add(DevelopmentCard.YEAR_OF_PLENTY);

        // add 2 'ROAD BUILDER' cards
        devCards.add(DevelopmentCard.ROAD_BUILDER);
        devCards.add(DevelopmentCard.ROAD_BUILDER);

        Collections.shuffle(devCards);

        developmentCardQueue.addAll(devCards);
    }

    public void allocateResourceCards(Map<ResourceType, Integer> allocatedCards) {
        int currentCount;
        int allocatedCount;
        int newCount;

        if (allocatedCards == null || allocatedCards.isEmpty())
            return;

        for (ResourceType resourceType : allocatedCards.keySet()) {
            if (allocatedCards.get(resourceType) == null)
                continue;

            allocatedCount = allocatedCards.get(resourceType);
            currentCount = resourceMap.get(resourceType);
            newCount = currentCount - allocatedCount;
            resourceMap.put(resourceType, (newCount >= 0 ? newCount : 0));
        }
    }

    public void deallocateResourceCards(Map<ResourceType, Integer> allocatedCards) {
        int currentCount;
        int allocatedCount;
        int newCount;

        if (allocatedCards == null || allocatedCards.isEmpty())
            return;

        for (ResourceType resourceType : allocatedCards.keySet()) {
            if (allocatedCards.get(resourceType) == null)
                continue;

            allocatedCount = allocatedCards.get(resourceType);
            currentCount = resourceMap.get(resourceType);
            newCount = currentCount + allocatedCount;
            resourceMap.put(resourceType, (newCount >= 0 ? newCount : 0));
        }
    }



    public void addResourceCards(Map<ResourceType, Integer> newCards) {
        int currentCount;
        int newCount;

        // add 'SHEEP' cards
        currentCount = resourceMap.get(ResourceType.SHEEP);
        newCount = newCards.get(ResourceType.SHEEP);
        resourceMap.put(ResourceType.SHEEP, currentCount + newCount);

        // add 'WHEAT' cards
        currentCount = resourceMap.get(ResourceType.WHEAT);
        newCount = newCards.get(ResourceType.WHEAT);
        resourceMap.put(ResourceType.WHEAT, currentCount + newCount);

        // add 'LUMBER' cards
        currentCount = resourceMap.get(ResourceType.LUMBER);
        newCount = newCards.get(ResourceType.LUMBER);
        resourceMap.put(ResourceType.LUMBER, currentCount + newCount);

        // add 'ORE' cards
        currentCount = resourceMap.get(ResourceType.ORE);
        newCount = newCards.get(ResourceType.ORE);
        resourceMap.put(ResourceType.ORE, currentCount + newCount);

        // add 'BRICK' cards
        currentCount = resourceMap.get(ResourceType.BRICK);
        newCount = newCards.get(ResourceType.BRICK);
        resourceMap.put(ResourceType.BRICK, currentCount + newCount);
    }

    public DevelopmentCard getDevelopmentCard() {
        return developmentCardQueue.pop();
    }

    public void addDevelopmentCard(DevelopmentCard newCard) {
        developmentCardQueue.push(newCard);
    }

}
