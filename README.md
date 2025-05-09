# Bounding Box Assessment

## Problem Statement
Given a 2D grid of characters where '*' represents a point and '-' represents empty space, find the largest non-overlapping bounding box that contains a group of connected asterisks.

### Visual Example
Input:
```
**-------
-*--**--***-
-----***--**
-------***--
```

Step 1 - Connected Components:
```
1 1 - - - - - - -
- 1 - - 2 2 - 3 3 3
- - - - - 2 2 2 - 2 2
- - - - - - - 3 3 3 -
```
(Numbers represent different connected groups)

Step 2 - Bounding Boxes:
```
[1 1]- - - - - - -
- [1]- - [2 2]- xxx -
- - - - - [2 2 2]xxx
- - - - - - - xxx -
```
([] represents Box 1 and 2, xxx represents Box 3)

Step 3 - Result:
```
[1 1]- - - - - - -
- [1]- - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
```
(Final largest non-overlapping box shown in [])

## Solution
The solution uses a breadth-first search (BFS) algorithm to:
1. Find all connected groups of asterisks
2. Calculate their bounding boxes
3. Remove overlapping boxes
4. Return the largest non-overlapping box

## Technical Details
- Language: Java 17
- Build Tool: Maven
- Testing Framework: JUnit 5

## Project Structure
```
src/
├── main/java/com/moneylion/bbox/
│   └── BoundingBox.java
├── test/java/com/moneylion/bbox/
│   └── BoundingBoxTest.java
└── main/resources/
    └── groups.txt
```

## How to Run
1. Build the project:
   ```bash
   mvn clean compile
   ```

2. Run the program:
   ```bash
   mvn exec:java
   ```

3. Run tests:
   ```bash
   mvn test
   ```

## Input Format
The input file (`groups.txt`) contains a grid where:
- '*' represents a point
- '-' represents empty space

Example:
```
**-------
-*--**--***-
-----***--**
-------***--
```

## Output Format
The program outputs the coordinates of the largest non-overlapping bounding box in the format:
```
(minRow,minCol)(maxRow,maxCol)
```
where coordinates are 1-indexed.

## Design Decisions

1. **BFS Implementation**
   - Chose BFS over DFS for finding connected components because:
     ```
     DFS Pattern:          BFS Pattern:
     * → * → *            * ← * ← *
     ↓                    ↓   ↓   ↓
     * → * → *            * ← * ← *
     ```
     - BFS explores in layers, better for finding minimal bounding boxes
     - Prevents deep recursion that could occur with DFS
     - More intuitive for grid-based traversal
     - Queue-based implementation is more memory-efficient for wide grids

2. **Data Structures**
   - `boolean[][] visited`: 
     ```java
     boolean[][] visited = new boolean[rows][cols];
     // Efficient O(1) lookup and marking
     visited[row][col] = true;
     ```
   - `Queue<Point>`: 
     ```java
     Queue<Point> queue = new LinkedList<>();
     queue.add(new Point(r, c));
     // FIFO ensures layer-by-layer exploration
     ```
   - `List<Box>`: 
     ```java
     List<Box> boxes = new ArrayList<>();
     // Dynamic sizing for unknown number of boxes
     ```

3. **Box Detection Algorithm**
   ```java
   // During BFS:
   minR = Math.min(minR, currentRow);
   maxR = Math.max(maxR, currentRow);
   minC = Math.min(minC, currentCol);
   maxC = Math.max(maxC, currentCol);
   ```
   - Updates boundaries in O(1) time
   - Single-pass algorithm
   - Memory efficient: only stores coordinates

4. **Overlap Detection**
   ```
   Box A:        Box B:        Overlap Check:
   (x1,y1)       (x3,y3)       if (x2 < x3 || x1 > x4 ||
   +------+      +------+          y2 < y3 || y1 > y4)
   |      |      |      |         return false;
   |      |      |      |
   +------+      +------+
   (x2,y2)       (x4,y4)
   ```
   - Handles all cases:
     - Partial overlap
     - Complete containment
     - Edge touching
     - No overlap

5. **Error Handling**
   ```java
   // Input validation
   if (lines == null || lines.isEmpty()) {
       return List.of();
   }
   
   // Resource management
   try (InputStream input = getResourceAsStream()) {
       // Safe resource usage
   } catch (Exception e) {
       // Proper error propagation
   }
   ```

6. **Testing Strategy**
   Test Matrix:
   ```
   ┌────────────────┬────────────┬────────────┐
   │ Test Case      │ Input Size │ Complexity │
   ├────────────────┼────────────┼────────────┤
   │ Empty Input    │    0x0     │    Low     │
   │ Single Box     │    2x2     │    Low     │
   │ Multiple Boxes │    4x6     │   Medium   │
   │ Complex Pattern│    4x10    │    High    │
   └────────────────┴────────────┴────────────┘
   ```

7. **Code Organization**
   ```
   BoundingBox.java
   ├── Point class (row, col)
   ├── Box class (coordinates + methods)
   └── Main class
       ├── computeBoundingBoxes()
       ├── readLines()
       └── main()
   ```
   - Clear class hierarchy
   - Separation of concerns
   - Immutable components

## Time and Space Complexity
- Time Complexity: O(R * C) where R is number of rows and C is number of columns
  - BFS traversal: O(R * C)
  - Overlap detection: O(B²) where B is number of boxes
  - Overall dominated by grid traversal
- Space Complexity: O(R * C)
  - Visited array: O(R * C)
  - Queue size: O(min(R, C))
  - Box list: O(B) where B is number of boxes 