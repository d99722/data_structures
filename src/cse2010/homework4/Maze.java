package cse2010.homework4;
/*
 * CSE2010 Homework #4:
 * Problem 3: Maze
 *
 * Complete the code below.
 */

import java.util.Arrays;

import cse2010.homework4.Location;

public class Maze {
    private int numRows;
    private int numCols;

    private int[][] maze;
    private boolean[][] visited = null;

    private Location entry; // Entry Location
    private Location exit;  // Exit Location

    public Maze(int[][] maze, Location entry, Location exit) {

        this.maze = maze;
        numRows = maze.length;
        numCols = maze[0].length;
        visited = new boolean[numRows][numCols]; // initialized to false

        this.entry = entry;
        this.exit = exit;
    }

    public void printMaze() {

        System.out.println("Maze[" + numRows + "][" + numCols + "]");
        System.out.println("Entry index = (" + entry.row + ", " + entry.col + ")");
        System.out.println("Exit index = (" + exit.row + ", " + exit.col + ")" + "\n");

        for (int i = 0; i < numRows; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }
        System.out.println();
    }

    public boolean findPath() {

        return moveTo(entry.row, entry.col);
    }

    private boolean moveTo(int row, int col) {
    	
    	//base case (closed)
    	if (visited[row][col] || maze[row][col]==1) {
    		return false;
    	}
    	
    	//base case (open)
    	else if (row==exit.row && col==exit.col) {
	    	return true;
	    } 
    	
    	//recursive calls
    	else {
	    	visited[row][col] = true;
	    	
	    	//search north
	    	if (row-1 >= 0 && moveTo(row-1,col)) {
	    		return true;
	    	}
	    	//search east
	    	if (col+1 < numCols && moveTo(row, col+1)) {
	    		return true;
	    	}
	    	//search south
	    	if (row+1 < numRows && moveTo(row+1, col) ) {
	    		return true;
	    	}
	    	//search west
	    	if (col-1 >=0 && moveTo(row,col-1)) {
	    		return true;
	    	} 
	    }
    	return false;

	}
}
