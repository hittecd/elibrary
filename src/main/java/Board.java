import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Board {

    private static final int HEX_COUNT = 19;
    private static final int CORNER_COUNT = 54;
    private static final int EDGE_COUNT = 72;

    private static final int LUMBER_HEX_COUNT = 4;
    private static final int SHEEP_HEX_COUNT = 4;
    private static final int WHEAT_HEX_COUNT = 4;
    private static final int BRICK_HEX_COUNT = 3;
    private static final int ORE_HEX_COUNT = 3;

    private final List<Hex> hexIndex = new ArrayList();
    private final List<Edge> edgeIndex = new ArrayList();
    private final List<Corner> cornerIndex = new ArrayList();

    public Board() {
        initEdges();
        initCorners();
        initHexes();

        assignCornersToEdges();

        assignEdgesToCorners();

        assignCornersToHexes();
        assignEdgesToHexes();
        assignResourceTypesToHexes();
        assignValuesToHexes();
    }

    private void initEdges() {
        for(int i=0; i<EDGE_COUNT; i++) {
            edgeIndex.add(i, new Edge(i));
        }
    }

    private void initCorners() {
        for(int i=0; i<CORNER_COUNT; i++) {
            cornerIndex.add(i, new Corner(i));
        }
    }

    private void initHexes() {
        for(int i=0; i<HEX_COUNT; i++) {
            hexIndex.add(i, new Hex(i));
        }
    }

    private void assignCornersToEdges() {
        List<Edge> edgeList = new ArrayList<Edge>();
        Corner corner;

        // corner 0
        corner = cornerIndex.get(0);

        edgeList.add(edgeIndex.get(0));
        edgeList.add(edgeIndex.get(1));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 1
        corner = cornerIndex.get(1);

        edgeList.add(edgeIndex.get(0));
        edgeList.add(edgeIndex.get(2));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 2
        corner = cornerIndex.get(2);

        edgeList.add(edgeIndex.get(3));
        edgeList.add(edgeIndex.get(5));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 3
        corner = cornerIndex.get(3);

        edgeList.add(edgeIndex.get(1));
        edgeList.add(edgeIndex.get(3));
        edgeList.add(edgeIndex.get(6));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 4
        corner = cornerIndex.get(4);

        edgeList.add(edgeIndex.get(2));
        edgeList.add(edgeIndex.get(4));
        edgeList.add(edgeIndex.get(7));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 5
        corner = cornerIndex.get(5);

        edgeList.add(edgeIndex.get(4));
        edgeList.add(edgeIndex.get(8));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 6
        corner = cornerIndex.get(6);

        edgeList.add(edgeIndex.get(9));
        edgeList.add(edgeIndex.get(12));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 7
        corner = cornerIndex.get(7);

        edgeList.add(edgeIndex.get(5));
        edgeList.add(edgeIndex.get(9));
        edgeList.add(edgeIndex.get(13));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 8
        corner = cornerIndex.get(8);

        edgeList.add(edgeIndex.get(6));
        edgeList.add(edgeIndex.get(10));
        edgeList.add(edgeIndex.get(14));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 9
        corner = cornerIndex.get(9);

        edgeList.add(edgeIndex.get(7));
        edgeList.add(edgeIndex.get(10));
        edgeList.add(edgeIndex.get(15));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 10
        corner = cornerIndex.get(10);

        edgeList.add(edgeIndex.get(8));
        edgeList.add(edgeIndex.get(11));
        edgeList.add(edgeIndex.get(16));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 11
        corner = cornerIndex.get(11);

        edgeList.add(edgeIndex.get(11));
        edgeList.add(edgeIndex.get(17));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 12
        corner = cornerIndex.get(12);

        edgeList.add(edgeIndex.get(12));
        edgeList.add(edgeIndex.get(20));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 13
        corner = cornerIndex.get(13);

        edgeList.add(edgeIndex.get(13));
        edgeList.add(edgeIndex.get(18));
        edgeList.add(edgeIndex.get(21));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 14
        corner = cornerIndex.get(14);

        edgeList.add(edgeIndex.get(14));
        edgeList.add(edgeIndex.get(18));
        edgeList.add(edgeIndex.get(22));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 15
        corner = cornerIndex.get(15);

        edgeList.add(edgeIndex.get(15));
        edgeList.add(edgeIndex.get(19));
        edgeList.add(edgeIndex.get(23));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 16
        corner = cornerIndex.get(16);

        edgeList.add(edgeIndex.get(16));
        edgeList.add(edgeIndex.get(19));
        edgeList.add(edgeIndex.get(24));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 17
        corner = cornerIndex.get(17);

        edgeList.add(edgeIndex.get(17));
        edgeList.add(edgeIndex.get(25));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 18
        corner = cornerIndex.get(18);

        edgeList.add(edgeIndex.get(20));
        edgeList.add(edgeIndex.get(26));
        edgeList.add(edgeIndex.get(29));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 19
        corner = cornerIndex.get(19);

        edgeList.add(edgeIndex.get(21));
        edgeList.add(edgeIndex.get(26));
        edgeList.add(edgeIndex.get(30));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 20
        corner = cornerIndex.get(20);

        edgeList.add(edgeIndex.get(22));
        edgeList.add(edgeIndex.get(27));
        edgeList.add(edgeIndex.get(31));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 21
        corner = cornerIndex.get(21);

        edgeList.add(edgeIndex.get(23));
        edgeList.add(edgeIndex.get(27));
        edgeList.add(edgeIndex.get(32));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 22
        corner = cornerIndex.get(22);

        edgeList.add(edgeIndex.get(24));
        edgeList.add(edgeIndex.get(28));
        edgeList.add(edgeIndex.get(33));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 23
        corner = cornerIndex.get(23);

        edgeList.add(edgeIndex.get(25));
        edgeList.add(edgeIndex.get(28));
        edgeList.add(edgeIndex.get(34));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 24
        corner = cornerIndex.get(24);

        edgeList.add(edgeIndex.get(29));
        edgeList.add(edgeIndex.get(37));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 25
        corner = cornerIndex.get(25);

        edgeList.add(edgeIndex.get(30));
        edgeList.add(edgeIndex.get(35));
        edgeList.add(edgeIndex.get(38));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 26
        corner = cornerIndex.get(26);

        edgeList.add(edgeIndex.get(31));
        edgeList.add(edgeIndex.get(35));
        edgeList.add(edgeIndex.get(39));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 27
        corner = cornerIndex.get(27);

        edgeList.add(edgeIndex.get(32));
        edgeList.add(edgeIndex.get(36));
        edgeList.add(edgeIndex.get(40));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 28
        corner = cornerIndex.get(28);

        edgeList.add(edgeIndex.get(33));
        edgeList.add(edgeIndex.get(36));
        edgeList.add(edgeIndex.get(41));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 29
        corner = cornerIndex.get(29);

        edgeList.add(edgeIndex.get(34));
        edgeList.add(edgeIndex.get(42));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 30
        corner = cornerIndex.get(30);

        edgeList.add(edgeIndex.get(37));
        edgeList.add(edgeIndex.get(43));
        edgeList.add(edgeIndex.get(46));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 31
        corner = cornerIndex.get(31);

        edgeList.add(edgeIndex.get(38));
        edgeList.add(edgeIndex.get(43));
        edgeList.add(edgeIndex.get(47));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 32
        corner = cornerIndex.get(32);

        edgeList.add(edgeIndex.get(39));
        edgeList.add(edgeIndex.get(44));
        edgeList.add(edgeIndex.get(48));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 33
        corner = cornerIndex.get(33);

        edgeList.add(edgeIndex.get(40));
        edgeList.add(edgeIndex.get(44));
        edgeList.add(edgeIndex.get(49));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 34
        corner = cornerIndex.get(34);

        edgeList.add(edgeIndex.get(41));
        edgeList.add(edgeIndex.get(45));
        edgeList.add(edgeIndex.get(50));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 35
        corner = cornerIndex.get(35);

        edgeList.add(edgeIndex.get(42));
        edgeList.add(edgeIndex.get(45));
        edgeList.add(edgeIndex.get(51));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 36
        corner = cornerIndex.get(36);

        edgeList.add(edgeIndex.get(46));
        edgeList.add(edgeIndex.get(54));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 37
        corner = cornerIndex.get(37);

        edgeList.add(edgeIndex.get(47));
        edgeList.add(edgeIndex.get(52));
        edgeList.add(edgeIndex.get(55));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 38
        corner = cornerIndex.get(38);

        edgeList.add(edgeIndex.get(48));
        edgeList.add(edgeIndex.get(52));
        edgeList.add(edgeIndex.get(56));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 39
        corner = cornerIndex.get(39);

        edgeList.add(edgeIndex.get(49));
        edgeList.add(edgeIndex.get(53));
        edgeList.add(edgeIndex.get(57));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 40
        corner = cornerIndex.get(40);

        edgeList.add(edgeIndex.get(50));
        edgeList.add(edgeIndex.get(53));
        edgeList.add(edgeIndex.get(58));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 41
        corner = cornerIndex.get(41);

        edgeList.add(edgeIndex.get(51));
        edgeList.add(edgeIndex.get(59));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 42
        corner = cornerIndex.get(42);

        edgeList.add(edgeIndex.get(54));
        edgeList.add(edgeIndex.get(60));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 43
        corner = cornerIndex.get(43);

        edgeList.add(edgeIndex.get(55));
        edgeList.add(edgeIndex.get(60));
        edgeList.add(edgeIndex.get(63));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 44
        corner = cornerIndex.get(44);

        edgeList.add(edgeIndex.get(56));
        edgeList.add(edgeIndex.get(61));
        edgeList.add(edgeIndex.get(64));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 45
        corner = cornerIndex.get(45);

        edgeList.add(edgeIndex.get(57));
        edgeList.add(edgeIndex.get(61));
        edgeList.add(edgeIndex.get(65));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 46
        corner = cornerIndex.get(46);

        edgeList.add(edgeIndex.get(58));
        edgeList.add(edgeIndex.get(62));
        edgeList.add(edgeIndex.get(66));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 47
        corner = cornerIndex.get(47);

        edgeList.add(edgeIndex.get(59));
        edgeList.add(edgeIndex.get(62));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 48
        corner = cornerIndex.get(48);

        edgeList.add(edgeIndex.get(63));
        edgeList.add(edgeIndex.get(67));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 49
        corner = cornerIndex.get(49);

        edgeList.add(edgeIndex.get(64));
        edgeList.add(edgeIndex.get(67));
        edgeList.add(edgeIndex.get(69));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 50
        corner = cornerIndex.get(50);

        edgeList.add(edgeIndex.get(65));
        edgeList.add(edgeIndex.get(68));
        edgeList.add(edgeIndex.get(70));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 51
        corner = cornerIndex.get(51);

        edgeList.add(edgeIndex.get(66));
        edgeList.add(edgeIndex.get(68));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 52
        corner = cornerIndex.get(52);

        edgeList.add(edgeIndex.get(69));
        edgeList.add(edgeIndex.get(71));
        corner.initEdges(edgeList);

        edgeList.clear();

        // corner 53
        corner = cornerIndex.get(53);

        edgeList.add(edgeIndex.get(70));
        edgeList.add(edgeIndex.get(71));
        corner.initEdges(edgeList);

        edgeList.clear();
    }

    private void assignEdgesToCorners() {
        List<Corner> cornerList = new ArrayList<Corner>();
        Edge edge;

        // edge 0
        edge = edgeIndex.get(0);

        cornerList.add(cornerIndex.get(0));
        cornerList.add(cornerIndex.get(1));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 1
        edge = edgeIndex.get(1);

        cornerList.add(cornerIndex.get(0));
        cornerList.add(cornerIndex.get(3));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 2
        edge = edgeIndex.get(2);

        cornerList.add(cornerIndex.get(1));
        cornerList.add(cornerIndex.get(4));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 3
        edge = edgeIndex.get(3);

        cornerList.add(cornerIndex.get(2));
        cornerList.add(cornerIndex.get(3));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 4
        edge = edgeIndex.get(4);

        cornerList.add(cornerIndex.get(4));
        cornerList.add(cornerIndex.get(5));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 5
        edge = edgeIndex.get(5);

        cornerList.add(cornerIndex.get(2));
        cornerList.add(cornerIndex.get(7));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 6
        edge = edgeIndex.get(6);

        cornerList.add(cornerIndex.get(3));
        cornerList.add(cornerIndex.get(8));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 7
        edge = edgeIndex.get(7);

        cornerList.add(cornerIndex.get(4));
        cornerList.add(cornerIndex.get(9));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 8
        edge = edgeIndex.get(8);

        cornerList.add(cornerIndex.get(5));
        cornerList.add(cornerIndex.get(10));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 9
        edge = edgeIndex.get(9);

        cornerList.add(cornerIndex.get(6));
        cornerList.add(cornerIndex.get(7));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 10
        edge = edgeIndex.get(10);

        cornerList.add(cornerIndex.get(8));
        cornerList.add(cornerIndex.get(9));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 11
        edge = edgeIndex.get(11);

        cornerList.add(cornerIndex.get(10));
        cornerList.add(cornerIndex.get(11));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 12
        edge = edgeIndex.get(12);

        cornerList.add(cornerIndex.get(6));
        cornerList.add(cornerIndex.get(12));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 13
        edge = edgeIndex.get(13);

        cornerList.add(cornerIndex.get(7));
        cornerList.add(cornerIndex.get(13));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 14
        edge = edgeIndex.get(14);

        cornerList.add(cornerIndex.get(8));
        cornerList.add(cornerIndex.get(14));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 15
        edge = edgeIndex.get(15);

        cornerList.add(cornerIndex.get(9));
        cornerList.add(cornerIndex.get(15));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 16
        edge = edgeIndex.get(16);

        cornerList.add(cornerIndex.get(10));
        cornerList.add(cornerIndex.get(16));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 17
        edge = edgeIndex.get(17);

        cornerList.add(cornerIndex.get(11));
        cornerList.add(cornerIndex.get(17));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 18
        edge = edgeIndex.get(18);

        cornerList.add(cornerIndex.get(13));
        cornerList.add(cornerIndex.get(14));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 19
        edge = edgeIndex.get(19);

        cornerList.add(cornerIndex.get(15));
        cornerList.add(cornerIndex.get(16));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 20
        edge = edgeIndex.get(20);

        cornerList.add(cornerIndex.get(12));
        cornerList.add(cornerIndex.get(18));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 21
        edge = edgeIndex.get(21);

        cornerList.add(cornerIndex.get(13));
        cornerList.add(cornerIndex.get(19));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 22
        edge = edgeIndex.get(22);

        cornerList.add(cornerIndex.get(14));
        cornerList.add(cornerIndex.get(20));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 23
        edge = edgeIndex.get(23);

        cornerList.add(cornerIndex.get(15));
        cornerList.add(cornerIndex.get(21));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 24
        edge = edgeIndex.get(24);

        cornerList.add(cornerIndex.get(16));
        cornerList.add(cornerIndex.get(22));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 25
        edge = edgeIndex.get(25);

        cornerList.add(cornerIndex.get(17));
        cornerList.add(cornerIndex.get(23));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 26
        edge = edgeIndex.get(26);

        cornerList.add(cornerIndex.get(18));
        cornerList.add(cornerIndex.get(19));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 27
        edge = edgeIndex.get(27);

        cornerList.add(cornerIndex.get(20));
        cornerList.add(cornerIndex.get(21));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 28
        edge = edgeIndex.get(28);

        cornerList.add(cornerIndex.get(22));
        cornerList.add(cornerIndex.get(23));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 29
        edge = edgeIndex.get(29);

        cornerList.add(cornerIndex.get(18));
        cornerList.add(cornerIndex.get(24));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 30
        edge = edgeIndex.get(30);

        cornerList.add(cornerIndex.get(19));
        cornerList.add(cornerIndex.get(25));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 31
        edge = edgeIndex.get(31);

        cornerList.add(cornerIndex.get(20));
        cornerList.add(cornerIndex.get(26));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 32
        edge = edgeIndex.get(32);

        cornerList.add(cornerIndex.get(21));
        cornerList.add(cornerIndex.get(27));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 33
        edge = edgeIndex.get(33);

        cornerList.add(cornerIndex.get(22));
        cornerList.add(cornerIndex.get(28));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 34
        edge = edgeIndex.get(34);

        cornerList.add(cornerIndex.get(23));
        cornerList.add(cornerIndex.get(29));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 35
        edge = edgeIndex.get(35);

        cornerList.add(cornerIndex.get(25));
        cornerList.add(cornerIndex.get(26));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 36
        edge = edgeIndex.get(36);

        cornerList.add(cornerIndex.get(27));
        cornerList.add(cornerIndex.get(28));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 37
        edge = edgeIndex.get(37);

        cornerList.add(cornerIndex.get(24));
        cornerList.add(cornerIndex.get(30));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 38
        edge = edgeIndex.get(38);

        cornerList.add(cornerIndex.get(25));
        cornerList.add(cornerIndex.get(31));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 39
        edge = edgeIndex.get(39);

        cornerList.add(cornerIndex.get(26));
        cornerList.add(cornerIndex.get(32));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 40
        edge = edgeIndex.get(40);

        cornerList.add(cornerIndex.get(27));
        cornerList.add(cornerIndex.get(33));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 41
        edge = edgeIndex.get(41);

        cornerList.add(cornerIndex.get(28));
        cornerList.add(cornerIndex.get(34));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 42
        edge = edgeIndex.get(42);

        cornerList.add(cornerIndex.get(29));
        cornerList.add(cornerIndex.get(35));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 43
        edge = edgeIndex.get(43);

        cornerList.add(cornerIndex.get(30));
        cornerList.add(cornerIndex.get(31));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 44
        edge = edgeIndex.get(44);

        cornerList.add(cornerIndex.get(32));
        cornerList.add(cornerIndex.get(33));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 45
        edge = edgeIndex.get(45);

        cornerList.add(cornerIndex.get(34));
        cornerList.add(cornerIndex.get(35));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 46
        edge = edgeIndex.get(46);

        cornerList.add(cornerIndex.get(30));
        cornerList.add(cornerIndex.get(36));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 47
        edge = edgeIndex.get(47);

        cornerList.add(cornerIndex.get(31));
        cornerList.add(cornerIndex.get(37));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 48
        edge = edgeIndex.get(48);

        cornerList.add(cornerIndex.get(32));
        cornerList.add(cornerIndex.get(38));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 49
        edge = edgeIndex.get(49);

        cornerList.add(cornerIndex.get(33));
        cornerList.add(cornerIndex.get(39));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 50
        edge = edgeIndex.get(50);

        cornerList.add(cornerIndex.get(34));
        cornerList.add(cornerIndex.get(40));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 51
        edge = edgeIndex.get(51);

        cornerList.add(cornerIndex.get(35));
        cornerList.add(cornerIndex.get(41));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 52
        edge = edgeIndex.get(52);

        cornerList.add(cornerIndex.get(37));
        cornerList.add(cornerIndex.get(38));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 53
        edge = edgeIndex.get(53);

        cornerList.add(cornerIndex.get(39));
        cornerList.add(cornerIndex.get(40));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 54
        edge = edgeIndex.get(54);

        cornerList.add(cornerIndex.get(36));
        cornerList.add(cornerIndex.get(42));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 55
        edge = edgeIndex.get(55);

        cornerList.add(cornerIndex.get(37));
        cornerList.add(cornerIndex.get(43));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 56
        edge = edgeIndex.get(56);

        cornerList.add(cornerIndex.get(38));
        cornerList.add(cornerIndex.get(44));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 57
        edge = edgeIndex.get(57);

        cornerList.add(cornerIndex.get(39));
        cornerList.add(cornerIndex.get(45));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 58
        edge = edgeIndex.get(58);

        cornerList.add(cornerIndex.get(40));
        cornerList.add(cornerIndex.get(46));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 59
        edge = edgeIndex.get(59);

        cornerList.add(cornerIndex.get(41));
        cornerList.add(cornerIndex.get(47));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 60
        edge = edgeIndex.get(60);

        cornerList.add(cornerIndex.get(42));
        cornerList.add(cornerIndex.get(43));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 61
        edge = edgeIndex.get(61);

        cornerList.add(cornerIndex.get(44));
        cornerList.add(cornerIndex.get(45));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 62
        edge = edgeIndex.get(62);

        cornerList.add(cornerIndex.get(46));
        cornerList.add(cornerIndex.get(47));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 63
        edge = edgeIndex.get(63);

        cornerList.add(cornerIndex.get(43));
        cornerList.add(cornerIndex.get(48));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 64
        edge = edgeIndex.get(64);

        cornerList.add(cornerIndex.get(44));
        cornerList.add(cornerIndex.get(49));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 65
        edge = edgeIndex.get(65);

        cornerList.add(cornerIndex.get(45));
        cornerList.add(cornerIndex.get(50));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 66
        edge = edgeIndex.get(66);

        cornerList.add(cornerIndex.get(46));
        cornerList.add(cornerIndex.get(51));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 67
        edge = edgeIndex.get(67);

        cornerList.add(cornerIndex.get(48));
        cornerList.add(cornerIndex.get(49));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 68
        edge = edgeIndex.get(68);

        cornerList.add(cornerIndex.get(50));
        cornerList.add(cornerIndex.get(51));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 69
        edge = edgeIndex.get(69);

        cornerList.add(cornerIndex.get(49));
        cornerList.add(cornerIndex.get(52));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 70
        edge = edgeIndex.get(70);

        cornerList.add(cornerIndex.get(50));
        cornerList.add(cornerIndex.get(53));
        edge.initCorners(cornerList);

        cornerList.clear();

        // edge 71
        edge = edgeIndex.get(71);

        cornerList.add(cornerIndex.get(52));
        cornerList.add(cornerIndex.get(53));
        edge.initCorners(cornerList);

        cornerList.clear();
    }

    private void assignEdgesToHexes() {
        List<Edge> edgeList = new ArrayList();
        Hex hex;

        // hex 0
        hex = hexIndex.get(0);

        edgeList.add(edgeIndex.get(0));
        edgeList.add(edgeIndex.get(1));
        edgeList.add(edgeIndex.get(2));
        edgeList.add(edgeIndex.get(6));
        edgeList.add(edgeIndex.get(7));
        edgeList.add(edgeIndex.get(10));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 1
        hex = hexIndex.get(1);

        edgeList.add(edgeIndex.get(3));
        edgeList.add(edgeIndex.get(5));
        edgeList.add(edgeIndex.get(6));
        edgeList.add(edgeIndex.get(13));
        edgeList.add(edgeIndex.get(14));
        edgeList.add(edgeIndex.get(18));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 2
        hex = hexIndex.get(2);

        edgeList.add(edgeIndex.get(4));
        edgeList.add(edgeIndex.get(7));
        edgeList.add(edgeIndex.get(8));
        edgeList.add(edgeIndex.get(15));
        edgeList.add(edgeIndex.get(16));
        edgeList.add(edgeIndex.get(19));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 3
        hex = hexIndex.get(3);

        edgeList.add(edgeIndex.get(9));
        edgeList.add(edgeIndex.get(12));
        edgeList.add(edgeIndex.get(13));
        edgeList.add(edgeIndex.get(20));
        edgeList.add(edgeIndex.get(21));
        edgeList.add(edgeIndex.get(26));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 4
        hex = hexIndex.get(4);

        edgeList.add(edgeIndex.get(10));
        edgeList.add(edgeIndex.get(14));
        edgeList.add(edgeIndex.get(15));
        edgeList.add(edgeIndex.get(22));
        edgeList.add(edgeIndex.get(23));
        edgeList.add(edgeIndex.get(27));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 5
        hex = hexIndex.get(5);

        edgeList.add(edgeIndex.get(11));
        edgeList.add(edgeIndex.get(16));
        edgeList.add(edgeIndex.get(17));
        edgeList.add(edgeIndex.get(24));
        edgeList.add(edgeIndex.get(25));
        edgeList.add(edgeIndex.get(28));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 6
        hex = hexIndex.get(6);

        edgeList.add(edgeIndex.get(18));
        edgeList.add(edgeIndex.get(21));
        edgeList.add(edgeIndex.get(22));
        edgeList.add(edgeIndex.get(30));
        edgeList.add(edgeIndex.get(31));
        edgeList.add(edgeIndex.get(35));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 7
        hex = hexIndex.get(7);

        edgeList.add(edgeIndex.get(19));
        edgeList.add(edgeIndex.get(23));
        edgeList.add(edgeIndex.get(24));
        edgeList.add(edgeIndex.get(32));
        edgeList.add(edgeIndex.get(33));
        edgeList.add(edgeIndex.get(36));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 8
        hex = hexIndex.get(8);

        edgeList.add(edgeIndex.get(26));
        edgeList.add(edgeIndex.get(29));
        edgeList.add(edgeIndex.get(30));
        edgeList.add(edgeIndex.get(37));
        edgeList.add(edgeIndex.get(38));
        edgeList.add(edgeIndex.get(43));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 9
        hex = hexIndex.get(9);

        edgeList.add(edgeIndex.get(27));
        edgeList.add(edgeIndex.get(31));
        edgeList.add(edgeIndex.get(32));
        edgeList.add(edgeIndex.get(39));
        edgeList.add(edgeIndex.get(40));
        edgeList.add(edgeIndex.get(44));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 10
        hex = hexIndex.get(10);

        edgeList.add(edgeIndex.get(28));
        edgeList.add(edgeIndex.get(33));
        edgeList.add(edgeIndex.get(34));
        edgeList.add(edgeIndex.get(41));
        edgeList.add(edgeIndex.get(42));
        edgeList.add(edgeIndex.get(45));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 11
        hex = hexIndex.get(11);

        edgeList.add(edgeIndex.get(35));
        edgeList.add(edgeIndex.get(38));
        edgeList.add(edgeIndex.get(39));
        edgeList.add(edgeIndex.get(47));
        edgeList.add(edgeIndex.get(48));
        edgeList.add(edgeIndex.get(52));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 12
        hex = hexIndex.get(12);

        edgeList.add(edgeIndex.get(36));
        edgeList.add(edgeIndex.get(40));
        edgeList.add(edgeIndex.get(41));
        edgeList.add(edgeIndex.get(49));
        edgeList.add(edgeIndex.get(50));
        edgeList.add(edgeIndex.get(53));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 13
        hex = hexIndex.get(13);

        edgeList.add(edgeIndex.get(43));
        edgeList.add(edgeIndex.get(46));
        edgeList.add(edgeIndex.get(47));
        edgeList.add(edgeIndex.get(54));
        edgeList.add(edgeIndex.get(55));
        edgeList.add(edgeIndex.get(60));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 14
        hex = hexIndex.get(14);

        edgeList.add(edgeIndex.get(44));
        edgeList.add(edgeIndex.get(48));
        edgeList.add(edgeIndex.get(49));
        edgeList.add(edgeIndex.get(56));
        edgeList.add(edgeIndex.get(57));
        edgeList.add(edgeIndex.get(61));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 15
        hex = hexIndex.get(15);

        edgeList.add(edgeIndex.get(45));
        edgeList.add(edgeIndex.get(50));
        edgeList.add(edgeIndex.get(51));
        edgeList.add(edgeIndex.get(58));
        edgeList.add(edgeIndex.get(59));
        edgeList.add(edgeIndex.get(62));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 16
        hex = hexIndex.get(16);

        edgeList.add(edgeIndex.get(52));
        edgeList.add(edgeIndex.get(55));
        edgeList.add(edgeIndex.get(56));
        edgeList.add(edgeIndex.get(63));
        edgeList.add(edgeIndex.get(64));
        edgeList.add(edgeIndex.get(67));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 17
        hex = hexIndex.get(17);

        edgeList.add(edgeIndex.get(53));
        edgeList.add(edgeIndex.get(57));
        edgeList.add(edgeIndex.get(58));
        edgeList.add(edgeIndex.get(65));
        edgeList.add(edgeIndex.get(66));
        edgeList.add(edgeIndex.get(68));
        hex.initEdges(edgeList);

        edgeList.clear();

        // hex 18
        hex = hexIndex.get(18);

        edgeList.add(edgeIndex.get(61));
        edgeList.add(edgeIndex.get(64));
        edgeList.add(edgeIndex.get(65));
        edgeList.add(edgeIndex.get(69));
        edgeList.add(edgeIndex.get(70));
        edgeList.add(edgeIndex.get(71));
        hex.initEdges(edgeList);

        edgeList.clear();

    }

    private void assignCornersToHexes() {
        List<Corner> cornerList = new ArrayList();
        Hex hex;

        // hex 0
        hex = hexIndex.get(0);

        cornerList.add(cornerIndex.get(0));
        cornerList.add(cornerIndex.get(1));
        cornerList.add(cornerIndex.get(3));
        cornerList.add(cornerIndex.get(4));
        cornerList.add(cornerIndex.get(8));
        cornerList.add(cornerIndex.get(9));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 1
        hex = hexIndex.get(1);

        cornerList.add(cornerIndex.get(2));
        cornerList.add(cornerIndex.get(3));
        cornerList.add(cornerIndex.get(7));
        cornerList.add(cornerIndex.get(8));
        cornerList.add(cornerIndex.get(13));
        cornerList.add(cornerIndex.get(14));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 2
        hex = hexIndex.get(2);

        cornerList.add(cornerIndex.get(4));
        cornerList.add(cornerIndex.get(5));
        cornerList.add(cornerIndex.get(9));
        cornerList.add(cornerIndex.get(10));
        cornerList.add(cornerIndex.get(15));
        cornerList.add(cornerIndex.get(16));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 3
        hex = hexIndex.get(3);

        cornerList.add(cornerIndex.get(6));
        cornerList.add(cornerIndex.get(7));
        cornerList.add(cornerIndex.get(12));
        cornerList.add(cornerIndex.get(13));
        cornerList.add(cornerIndex.get(18));
        cornerList.add(cornerIndex.get(19));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 4
        hex = hexIndex.get(4);

        cornerList.add(cornerIndex.get(8));
        cornerList.add(cornerIndex.get(9));
        cornerList.add(cornerIndex.get(14));
        cornerList.add(cornerIndex.get(15));
        cornerList.add(cornerIndex.get(20));
        cornerList.add(cornerIndex.get(21));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 5
        hex = hexIndex.get(5);

        cornerList.add(cornerIndex.get(10));
        cornerList.add(cornerIndex.get(11));
        cornerList.add(cornerIndex.get(16));
        cornerList.add(cornerIndex.get(17));
        cornerList.add(cornerIndex.get(22));
        cornerList.add(cornerIndex.get(23));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 6
        hex = hexIndex.get(6);

        cornerList.add(cornerIndex.get(13));
        cornerList.add(cornerIndex.get(14));
        cornerList.add(cornerIndex.get(19));
        cornerList.add(cornerIndex.get(20));
        cornerList.add(cornerIndex.get(25));
        cornerList.add(cornerIndex.get(26));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 7
        hex = hexIndex.get(7);

        cornerList.add(cornerIndex.get(15));
        cornerList.add(cornerIndex.get(16));
        cornerList.add(cornerIndex.get(21));
        cornerList.add(cornerIndex.get(22));
        cornerList.add(cornerIndex.get(27));
        cornerList.add(cornerIndex.get(28));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 8
        hex = hexIndex.get(8);

        cornerList.add(cornerIndex.get(18));
        cornerList.add(cornerIndex.get(19));
        cornerList.add(cornerIndex.get(24));
        cornerList.add(cornerIndex.get(25));
        cornerList.add(cornerIndex.get(30));
        cornerList.add(cornerIndex.get(31));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 9
        hex = hexIndex.get(9);

        cornerList.add(cornerIndex.get(20));
        cornerList.add(cornerIndex.get(21));
        cornerList.add(cornerIndex.get(26));
        cornerList.add(cornerIndex.get(27));
        cornerList.add(cornerIndex.get(32));
        cornerList.add(cornerIndex.get(33));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 10
        hex = hexIndex.get(10);

        cornerList.add(cornerIndex.get(22));
        cornerList.add(cornerIndex.get(23));
        cornerList.add(cornerIndex.get(28));
        cornerList.add(cornerIndex.get(29));
        cornerList.add(cornerIndex.get(34));
        cornerList.add(cornerIndex.get(35));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 11
        hex = hexIndex.get(11);

        cornerList.add(cornerIndex.get(25));
        cornerList.add(cornerIndex.get(26));
        cornerList.add(cornerIndex.get(31));
        cornerList.add(cornerIndex.get(32));
        cornerList.add(cornerIndex.get(37));
        cornerList.add(cornerIndex.get(38));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 12
        hex = hexIndex.get(12);

        cornerList.add(cornerIndex.get(27));
        cornerList.add(cornerIndex.get(28));
        cornerList.add(cornerIndex.get(33));
        cornerList.add(cornerIndex.get(34));
        cornerList.add(cornerIndex.get(39));
        cornerList.add(cornerIndex.get(40));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 13
        hex = hexIndex.get(13);

        cornerList.add(cornerIndex.get(30));
        cornerList.add(cornerIndex.get(31));
        cornerList.add(cornerIndex.get(36));
        cornerList.add(cornerIndex.get(37));
        cornerList.add(cornerIndex.get(42));
        cornerList.add(cornerIndex.get(43));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 14
        hex = hexIndex.get(14);

        cornerList.add(cornerIndex.get(32));
        cornerList.add(cornerIndex.get(33));
        cornerList.add(cornerIndex.get(38));
        cornerList.add(cornerIndex.get(39));
        cornerList.add(cornerIndex.get(44));
        cornerList.add(cornerIndex.get(45));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 15
        hex = hexIndex.get(15);

        cornerList.add(cornerIndex.get(34));
        cornerList.add(cornerIndex.get(35));
        cornerList.add(cornerIndex.get(40));
        cornerList.add(cornerIndex.get(41));
        cornerList.add(cornerIndex.get(46));
        cornerList.add(cornerIndex.get(47));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 16
        hex = hexIndex.get(16);

        cornerList.add(cornerIndex.get(37));
        cornerList.add(cornerIndex.get(38));
        cornerList.add(cornerIndex.get(43));
        cornerList.add(cornerIndex.get(44));
        cornerList.add(cornerIndex.get(48));
        cornerList.add(cornerIndex.get(49));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 17
        hex = hexIndex.get(17);

        cornerList.add(cornerIndex.get(39));
        cornerList.add(cornerIndex.get(40));
        cornerList.add(cornerIndex.get(45));
        cornerList.add(cornerIndex.get(46));
        cornerList.add(cornerIndex.get(50));
        cornerList.add(cornerIndex.get(51));
        hex.initCorners(cornerList);

        cornerList.clear();

        // hex 18
        hex = hexIndex.get(18);

        cornerList.add(cornerIndex.get(44));
        cornerList.add(cornerIndex.get(45));
        cornerList.add(cornerIndex.get(49));
        cornerList.add(cornerIndex.get(50));
        cornerList.add(cornerIndex.get(52));
        cornerList.add(cornerIndex.get(53));
        hex.initCorners(cornerList);

        cornerList.clear();

    }

    private void assignResourceTypesToHexes() {
        List<ResourceType> resources = new ArrayList();
        Hex hex;
        ResourceType resourceType;

        // add 'LUMBER' resources
        for(int i=0; i<LUMBER_HEX_COUNT; i++) {
            resources.add(ResourceType.LUMBER);
        }

        // add 'WHEAT' resources
        for(int i=0; i<WHEAT_HEX_COUNT; i++) {
            resources.add(ResourceType.WHEAT);
        }

        // add 'SHEEP' resources
        for(int i=0; i<SHEEP_HEX_COUNT; i++) {
            resources.add(ResourceType.SHEEP);
        }

        // add 'BRICK' resources
        for(int i=0; i<BRICK_HEX_COUNT; i++) {
            resources.add(ResourceType.BRICK);
        }

        // add 'ORE' resources
        for(int i=0; i<ORE_HEX_COUNT; i++) {
            resources.add(ResourceType.ORE);
        }

        // add 'DESERT' resource
        resources.add(ResourceType.DESERT);

        Collections.shuffle(resources);

        // assign resource types
        for(int i=0; i<HEX_COUNT; i++) {
            hex = hexIndex.get(i);
            resourceType = resources.get(i);

            hex.setHexResourceType(resourceType);
        }
    }

    private void assignValuesToHexes() {
        List<Integer> hexValues = new ArrayList();
        Hex hex;
        Integer hexValue;

        // add hex values
        hexValues.add(2);
        hexValues.add(3);
        hexValues.add(3);
        hexValues.add(4);
        hexValues.add(4);
        hexValues.add(5);
        hexValues.add(5);
        hexValues.add(6);
        hexValues.add(6);
        hexValues.add(8);
        hexValues.add(8);
        hexValues.add(9);
        hexValues.add(9);
        hexValues.add(10);
        hexValues.add(10);
        hexValues.add(11);
        hexValues.add(11);
        hexValues.add(12);

        Collections.shuffle(hexValues);

        // assign hex values
        int i = 0;
        int j = 0;
        while(i<HEX_COUNT) {
            hex = hexIndex.get(i);

            // the desert tile is not assigned a hex value so we skip over it;
            // this should only happen once
            if(hex.getHexResourceType().equals(ResourceType.DESERT)) {
                i++;
                continue;
            }

            hexValue = hexValues.get(j);
            hex.setHexValue(hexValue);

            i++;
            j++;
        }
    }
}
