package api.Tests;

import api.EdgeData;
import api.graph.EdgeDataClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataClassTest {
    EdgeData edgeDataTest = new EdgeDataClass(0, 0 , 1.783);

    @Test
    void getSrc() {
        assertEquals(0, edgeDataTest.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(8, edgeDataTest.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(1.783, edgeDataTest.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("src: 0, dest: 8, weight: 1.783", edgeDataTest.getInfo());
    }

    @Test
    void setInfo() {
        String toSet = "this is a test";
        edgeDataTest.setInfo(toSet);
        assertEquals(toSet, edgeDataTest.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, edgeDataTest.getTag());
    }

    @Test
    void setTag() {
        edgeDataTest.setTag(1);
        assertEquals(1, edgeDataTest.getTag());
    }
}