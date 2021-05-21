package P3;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipGraphTest {

    @Test
    void addVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person Rose = new Person("Rose");
        Person Jay = new Person("Jay");
        Person Joe = new Person("Joe");
        graph.addVertex(Rose);
        assertEquals("Rose",graph.crow.get(graph.crow.size()-1).myname());
        graph.addVertex(Jay);
        assertEquals(Jay,graph.crow.get(graph.crow.size()-1));
        graph.addVertex(Joe);
        assertEquals(Joe,graph.crow.get(graph.crow.size()-1));
    }

    @Test
    void addEdge() {
        FriendshipGraph graph = new FriendshipGraph();
        Person Rose = new Person("Rose");
        Person Jay = new Person("Jay");
        graph.addVertex(Rose);
        graph.addVertex(Jay);
        assertEquals(0,graph.crow.get(0).Freiends().size());
        assertEquals(0,graph.crow.get(1).Freiends().size());
        graph.addEdge(Rose,Jay);
        assertEquals(Jay,graph.crow.get(0).Freiends().get(0));
        graph.addEdge(Jay,Rose);
        assertEquals(Rose,graph.crow.get(1).Freiends().get(0));
    }

    @Test
    void getDistance() {
        FriendshipGraph graph = new FriendshipGraph();
        Person Rose = new Person("Rose");
        Person Jay = new Person("Jay");
        Person Joe = new Person("Joe");
        Person Alice = new Person("Alice");
        graph.addVertex(Rose);
        graph.addVertex(Jay);
        graph.addVertex(Joe);
        graph.addVertex(Alice);
        graph.addEdge(Rose,Jay);
        assertEquals(1,graph.getDistance(Rose,Jay));
        graph.addEdge(Jay,Joe);
        assertEquals(2,graph.getDistance(Rose,Joe));
        graph.addEdge(Rose,Joe);
        assertEquals(1,graph.getDistance(Rose,Joe));
        assertEquals(-1,graph.getDistance(Rose,Alice));
    }
}