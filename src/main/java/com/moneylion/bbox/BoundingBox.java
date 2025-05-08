package com.moneylion.bbox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BoundingBox {

    private static final char ASTERISK = '*';

    static class Point {
        int row, col;
        Point(int row, int col) { this.row = row; this.col = col; }
    }

    public static class Box {
        int minRow, minCol, maxRow, maxCol;

        Box(int minRow, int minCol, int maxRow, int maxCol) {
            this.minRow = minRow;
            this.minCol = minCol;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
        }

        int area() {
            return (maxRow - minRow + 1) * (maxCol - minCol + 1);
        }

        boolean overlaps(Box other) {
            return !(this.maxRow < other.minRow || this.minRow > other.maxRow ||
                     this.maxCol < other.minCol || this.minCol > other.maxCol);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)(%d,%d)", minRow + 1, minCol + 1, maxRow + 1, maxCol + 1);
        }
    }

    public static List<Box> computeBoundingBoxes(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return List.of();
        }
        
        int rows = lines.size();
        int cols = lines.get(0).length();
        boolean[][] visited = new boolean[rows][cols];
        List<Box> boxes = new ArrayList<>();

        int[][] directions = { {0,1}, {1,0}, {-1,0}, {0,-1} };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c] && lines.get(r).charAt(c) == ASTERISK) {
                    Queue<Point> queue = new LinkedList<>();
                    queue.add(new Point(r, c));
                    visited[r][c] = true;

                    int minR = r, maxR = r, minC = c, maxC = c;

                    while (!queue.isEmpty()) {
                        Point p = queue.poll();
                        for (int[] d : directions) {
                            int nr = p.row + d[0];
                            int nc = p.col + d[1];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
                                !visited[nr][nc] && lines.get(nr).charAt(nc) == ASTERISK) {
                                visited[nr][nc] = true;
                                queue.add(new Point(nr, nc));
                                minR = Math.min(minR, nr);
                                maxR = Math.max(maxR, nr);
                                minC = Math.min(minC, nc);
                                maxC = Math.max(maxC, nc);
                            }
                        }
                    }

                    boxes.add(new Box(minR, minC, maxR, maxC));
                }
            }
        }

        // Remove overlapping boxes
        List<Box> nonOverlapping = new ArrayList<>();
        for (Box b : boxes) {
            boolean overlaps = false;
            for (Box other : boxes) {
                if (b != other && b.overlaps(other)) {
                    overlaps = true;
                    break;
                }
            }
            if (!overlaps) {
                nonOverlapping.add(b);
            }
        }

        // Find the largest by area
        Box largest = null;
        for (Box b : nonOverlapping) {
            if (largest == null || b.area() > largest.area()) {
                largest = b;
            }
        }

        return largest != null ? List.of(largest) : List.of();
    }

    public static List<String> readLines(InputStream input) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {  // Skip empty lines
                    lines.add(line);
                    System.out.println("Read line: " + line);
                }
            }
        }
        return lines;
    }

    public static void main(String[] args) {
        try {
            // Read input from resources
            InputStream input = BoundingBox.class.getClassLoader().getResourceAsStream("groups.txt");
            if (input == null) {
                System.err.println("Error: Could not find groups.txt in resources");
                return;
            }
            List<String> inputLines = readLines(input);
            
            // Compute bounding boxes
            List<Box> result = computeBoundingBoxes(inputLines);
            
            // Print each bounding box
            for (Box box : result) {
                System.out.println(box);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
