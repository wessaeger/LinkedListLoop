package linkedlistloop;

import java.util.ArrayList;
import java.util.List;

class LinkedListLoop {

    private static class Cell {
        private int r;
        private int c;

        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "(" + r + "," + c + ")";
        }
    }

    private static void printMap(int[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                System.out.print(map[row][column] == 0 ? "O" : "X");
            }
            System.out.println();
        }
    }

    private static void printVisited(boolean[][] visited) {
        for (int row = 0; row < visited.length; row++) {
            for (int column = 0; column < visited[row].length; column++) {
                System.out.print(visited[row][column] ? "T" : "F");
            }
            System.out.println();
        }
    }

    private static void initVisited(boolean[][] visited) {
        for (int row = 0; row < visited.length; row++) {
            for (int column = 0; column < visited[row].length; column++) {
                visited[row][column] = false;
            }
        }
    }

    private static void initIslandMap(int[][] islandMap) {
        for (int row = 0; row < islandMap.length; row++) {
            for (int column = 0; column < islandMap[row].length; column++) {
                islandMap[row][column] = 0;
            }
        }
    }

    private static boolean hasBeenVisited(boolean[][] visited, int row, int col) {
        return visited[row][col];
    }

    private static boolean isIslandCell(int[][] map, int row, int col) {
        return (map[row][col] != 0);
    }

    private static List<Cell> getAdjacentCells(int[][] map, int row, int col, boolean[][] visited) {
        List<Cell> result = new ArrayList<>();

        for (int r = Math.max(row-1,0); r <= Math.min(row+1, map.length-1); r++) {
            for (int c = Math.max(col-1, 0); c <= Math.min(col+1, map[0].length-1); c++) {
                if (!(r == row && c == col) && isIslandCell(map, r, c) && !hasBeenVisited(visited, r, c)) {
                    result.add(new Cell(r, c));
                }
            }
        }
        return result;
    }

    private static String toString(List<Cell> cells) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < cells.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(cells.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    private static int findIslandCount(int[][] map) {
        return findIslandCount(map, false);
    }

    private static int findIslandCount(int[][] map, boolean printIslands) {
        int islandCount = 0;

        boolean[][] visited = new boolean[map.length][map[0].length];
        initVisited(visited);

        int[][] islandMap = new int[map.length][map[0].length];
        initIslandMap(islandMap);

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                if (isIslandCell(map, r, c) && !hasBeenVisited(visited, r, c)) {
                    initIslandMap(islandMap);
                    getIsland(map, r, c, visited, islandMap);
                    islandCount++;
                    if (printIslands) {
                        System.out.println("\nIsland #" + islandCount);
                        System.out.println("===========");
                        printMap(islandMap);
                    }
                }
            }
        }
        return islandCount;
    }

    private static void getIsland(int[][] map, int r, int c, boolean[][] visited, int[][] islandMap) {
        if (isIslandCell(map, r, c) && !hasBeenVisited(visited, r, c)) {
            visited[r][c] = true;
            islandMap[r][c] = 1;
            List<Cell> adjacentCells = getAdjacentCells(map, r, c, visited);
            if (adjacentCells.isEmpty()) {
                return;
            }
            for (Cell cell : adjacentCells) {
                getIsland(map, cell.getR(), cell.getC(), visited, islandMap);
            }
        }
    }

    public static void main(String[] args) {
        int[][] map = {{1,1,0,0},
                {1,1,0,1},
                {0,0,0,0},
                {0,1,1,0},
                {0,0,1,1}};

        int islandCount;

        islandCount = findIslandCount(map);
        System.out.println("Island Count: " + islandCount);

        int[][] map2 = {
                {0,0,0,0,1,0,0,0,1,0},
                {0,1,0,1,0,0,0,1,0,0},
                {0,1,0,1,0,0,0,0,0,0},
                {0,0,0,1,1,1,0,0,1,0},
                {0,0,0,0,0,1,0,1,0,1},
                {0,0,0,0,1,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,1,0,0},
                {0,1,0,0,0,0,0,0,0,0},
                {1,0,1,1,0,0,0,0,0,0}};

        islandCount = findIslandCount(map2, true);
        System.out.println("Island Count: " + islandCount);

        System.out.println("Done.");
    }
}