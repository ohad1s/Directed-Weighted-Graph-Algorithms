package api.Tests;

import api.GeoLocation;
import api.GeoLocationClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationClassTest {
    public static final double EPS = 0.01;
    GeoLocation geoTest1 = new GeoLocationClass(1.5, 2, 5.2);
    GeoLocation geoTest2 = new GeoLocationClass(1,7,3);
    @Test
    void x() {
        assertEquals(1.5, geoTest1.x());
    }

    @Test
    void y() {
        
        assertEquals(2.0, geoTest1.y());
    }

    @Test
    void z() {
        assertEquals(5.2, geoTest1.z());
    }

    @Test
    void distance() {
        assertEquals(5.49, geoTest1.distance(geoTest2), EPS);
    }
}