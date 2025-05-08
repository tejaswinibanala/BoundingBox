package com.moneylion.bbox;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BoundingBoxTest {

    @Test
    public void testSimpleBox() {
        List<String> input = Arrays.asList(
            "**----",
            "-*----"
        );
        List<BoundingBox.Box> result = BoundingBox.computeBoundingBoxes(input);
        assertEquals(1, result.size());
        BoundingBox.Box box = result.get(0);
        assertEquals("(1,1)(2,2)", box.toString());
    }

    @Test
    public void testMultipleBoxes() {
        List<String> input = Arrays.asList(
            "**----",
            "-*----",
            "----**",
            "----*-"
        );
        List<BoundingBox.Box> result = BoundingBox.computeBoundingBoxes(input);
        assertEquals(1, result.size());
        BoundingBox.Box box = result.get(0);
        assertEquals("(1,1)(2,2)", box.toString());
    }

    @Test
    public void testEmptyInput() {
        List<String> input = Arrays.asList();
        List<BoundingBox.Box> result = BoundingBox.computeBoundingBoxes(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNoAsterisks() {
        List<String> input = Arrays.asList(
            "------",
            "------"
        );
        List<BoundingBox.Box> result = BoundingBox.computeBoundingBoxes(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testComplexPattern() {
        List<String> input = Arrays.asList(
            "**-------",
            "-*--**--***-",
            "-----***--**",
            "-------***--"
        );
        List<BoundingBox.Box> result = BoundingBox.computeBoundingBoxes(input);
        assertEquals(1, result.size());
        BoundingBox.Box box = result.get(0);
        assertEquals("(1,1)(2,2)", box.toString());
    }
}
