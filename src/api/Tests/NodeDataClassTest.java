package api.Tests;

import api.GeoLocation;
import api.GeoLocationClass;
import api.NodeData;
import api.NodeDataClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataClassTest {
    NodeData nodeTest = new NodeDataClass(0, 1.5 , 0.7, 13);

    @Test
    void getKey() {
        assertEquals(0, nodeTest.getKey());
    }

    @Test
    void getLocation() {
        GeoLocation toCheck = nodeTest.getLocation();
        assertEquals(1.5, toCheck.x());
        assertEquals(0.7, toCheck.y());
        assertEquals(13, toCheck.z());
    }

    @Test
    void setLocation() {
        GeoLocation toSet = new GeoLocationClass(0, 0, 0);
        nodeTest.setLocation(toSet);
        GeoLocation toCheck = nodeTest.getLocation();
        assertEquals(0, toCheck.x());
        assertEquals(0, toCheck.y());
        assertEquals(0, toCheck.z());
    }

    @Test
    void getWeight() {
        assertEquals(0, nodeTest.getWeight());
    }

    @Test
    void setWeight() {
        nodeTest.setWeight(1);
        assertEquals(1, nodeTest.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("key: 0, location: (1.5, 0.7, 13.0), weight: 1.823, tag: 0", nodeTest.getInfo());
    }

    @Test
    void setInfo() {
        String toSet = "this is a test";
        nodeTest.setInfo(toSet);
        assertEquals(toSet, nodeTest.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, nodeTest.getTag());
    }

    @Test
    void setTag() {
        nodeTest.setTag(2);
        assertEquals(2, nodeTest.getTag());
    }
}