import java.util.*;

public class Bank {

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
        //initialize 19 of each Resource Type
        resourceMap.put(ResourceType.LUMBER, 19);
        resourceMap.put(ResourceType.WHEAT, 19);
        resourceMap.put(ResourceType.SHEEP, 19);
        resourceMap.put(ResourceType.BRICK, 19);
        resourceMap.put(ResourceType.ORE, 19);
    }

    private void initDevelopmentCardQueue() {
        List<DevelopmentCard> devCards = new ArrayList();

        //add 14 'KNIGHT' cards
        for(int i=1; i<=14; i++)
            devCards.add(DevelopmentCard.KNIGHT);

        //add 5 'VICTORY POINT' cards
        for(int i=1; i<=5; i++)
            devCards.add(DevelopmentCard.VICTORY_POINT);

        //add 2 'MONOPOLY' cards
        devCards.add(DevelopmentCard.MONOPOLY);
        devCards.add(DevelopmentCard.MONOPOLY);

        //add 2 'YEAR OF PLENTY' cards
        devCards.add(DevelopmentCard.YEAR_OF_PLENTY);
        devCards.add(DevelopmentCard.YEAR_OF_PLENTY);

        //add 2 'ROAD BUILDER' cards
        devCards.add(DevelopmentCard.ROAD_BUILDER);
        devCards.add(DevelopmentCard.ROAD_BUILDER);

        Collections.shuffle(devCards);

        developmentCardQueue.addAll(devCards);
    }

    public void allocateResourceCards(Map<ResourceType, Integer> allocatedCards) {
        int currentCount;
        int allocatedCount;
        int newCount;

        //allocate 'SHEEP' cards
        currentCount = resourceMap.get(ResourceType.SHEEP);
        allocatedCount = allocatedCards.get(ResourceType.SHEEP);
        newCount = currentCount - allocatedCount;
        resourceMap.put(ResourceType.SHEEP, (newCount >= 0 ? newCount : 0));

        //allocate 'WHEAT' cards
        currentCount = resourceMap.get(ResourceType.WHEAT);
        allocatedCount = allocatedCards.get(ResourceType.WHEAT);
        newCount = currentCount - allocatedCount;
        resourceMap.put(ResourceType.WHEAT, (newCount >= 0 ? newCount : 0));

        //allocate 'LUMBER' cards
        currentCount = resourceMap.get(ResourceType.LUMBER);
        allocatedCount = allocatedCards.get(ResourceType.LUMBER);
        newCount = currentCount - allocatedCount;
        resourceMap.put(ResourceType.LUMBER, (newCount >= 0 ? newCount : 0));

        //allocate 'ORE' cards
        currentCount = resourceMap.get(ResourceType.ORE);
        allocatedCount = allocatedCards.get(ResourceType.ORE);
        newCount = currentCount - allocatedCount;
        resourceMap.put(ResourceType.ORE, (newCount >= 0 ? newCount : 0));

        //allocate 'BRICK' cards
        currentCount = resourceMap.get(ResourceType.BRICK);
        allocatedCount = allocatedCards.get(ResourceType.BRICK);
        newCount = currentCount - allocatedCount;
        resourceMap.put(ResourceType.BRICK, (newCount >= 0 ? newCount : 0));
    }

    public void addResourceCards(Hashtable<ResourceType, Integer> newCards) {
        int currentCount;
        int newCount;

        //add 'SHEEP' cards
        currentCount = resourceMap.get(ResourceType.SHEEP);
        newCount = newCards.get(ResourceType.SHEEP);
        resourceMap.put(ResourceType.SHEEP, currentCount + newCount);

        //add 'WHEAT' cards
        currentCount = resourceMap.get(ResourceType.WHEAT);
        newCount = newCards.get(ResourceType.WHEAT);
        resourceMap.put(ResourceType.WHEAT, currentCount + newCount);

        //add 'LUMBER' cards
        currentCount = resourceMap.get(ResourceType.LUMBER);
        newCount = newCards.get(ResourceType.LUMBER);
        resourceMap.put(ResourceType.LUMBER, currentCount + newCount);

        //add 'ORE' cards
        currentCount = resourceMap.get(ResourceType.ORE);
        newCount = newCards.get(ResourceType.ORE);
        resourceMap.put(ResourceType.ORE, currentCount + newCount);

        //add 'BRICK' cards
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
