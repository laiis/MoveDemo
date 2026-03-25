# MoveDemo

A Java-based simulation and solver for the Jump Game (移位跳棋).

## Overview
This project simulates a jump game where white and black pieces need to swap positions in a grid.
- Rules:
  1. A piece can move to an adjacent empty space.
  2. A piece can jump over an adjacent piece to an empty space.
  3. Minimum steps formula: `steps = N * (N + 2)`

## Quick Start

### Build
Use Gradle to build the project:
```bash
./gradlew build
```

### Run Benchmark
Run the `Main` class to execute performance benchmarks for different strategies (TheBest, BFS, AStar):
```bash
./gradlew run
```

### Strategies
- **TheBestStrategy**: Optimized heuristic approach.
- **BFSStrategy**: Breadth-First Search for optimal path finding (for small N).
- **AStarStrategy**: A* search using heuristics for efficiency.
- **ExhaustiveStrategy**: Brute-force approach for verification.

## story

Place three white pieces (O) and three black pieces (X) into the grid as shown below:

| O | O | O | space | X | X | X |

The question is:

**How can we move the three white pieces to the positions occupied by the black pieces?**

Rules:

1. A piece can be moved to an adjacent empty space.
2. A piece can jump over an adjacent piece and land on an empty space.
3. The formula for the minimum number of steps in a jump game with equal-length moves is as follows: steps = N * (N + 2)

Extended question:

If we replace the white pieces with lowercase English letters and the black pieces with uppercase English letters, please list the number of steps and the moves for all letters from 1 to 26.


ref: [個人移位跳棋遊戲的探討](https://www.ntsec.edu.tw/science/detail.aspx?a=21&cat=38&sid=402)

