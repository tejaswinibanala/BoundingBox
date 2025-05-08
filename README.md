# Bounding Box Assessment

## Problem Statement
Given a 2D grid of characters where '*' represents a point and '-' represents empty space, find the largest non-overlapping bounding box that contains a group of connected asterisks.

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
1. Used BFS for finding connected components as it's efficient for grid-based problems
2. Implemented non-overlapping box detection to ensure valid results
3. Added comprehensive test cases covering various scenarios
4. Used Java's built-in collections for efficient data structures
5. Implemented proper error handling and input validation

## Time and Space Complexity
- Time Complexity: O(R * C) where R is number of rows and C is number of columns
- Space Complexity: O(R * C) for the visited array and queue 