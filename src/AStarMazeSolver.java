//JACOB LEBLANC - 202101683

import maze.Cell;
import maze.Maze;
import maze.MazeSolver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

public class AStarMazeSolver implements MazeSolver {

    @Override
    public Set<Cell> solve(Maze maze) {

        // make my Priority Queue
        LinkedPriorityQueue<Cell> myQueue = new LinkedPriorityQueue<>();

        // using a HashMap to keep track of the Cell that we took to get there
        // Key: the Cell
        // Value: the Cell's predecessor (previous cell)
        Map<Cell, Cell> previousCell = new HashMap<>();

        // keep track of all checked Cell's
        Set<Cell> checked = new HashSet<>();

        // Starting Cell's coordinates
        int startX = maze.getStart().getX();
        int startY = maze.getStart().getY();

        // Ending Cell's coordinates
        int endX = maze.getEnd().getX();
        int endY = maze.getEnd().getY();

        // enqueue the start cell
        myQueue.enqueue(maze.getStart(), 0);

        // while the Queue isn't empty
        while (!myQueue.isEmpty()) {
            // temporary variable keeping track of most recent dequeue
            Cell temp = myQueue.dequeue();
            // if we just dequeued the end, we're done
            if (temp.isEnd()) {
                break;
            }
            else {
                // add temp to checked cells
                checked.add(temp);
                // UP
                // if the location we want to go is in the make, is visitable and not in the checked set
                if (maze.isLocationInMaze(temp.getX(), temp.getY() - 1) && maze.getCell(temp.getX(), temp.getY() - 1).isVisitable() && !checked.contains(maze.getCell(temp.getX(), temp.getY() - 1))) {
                    Cell up = maze.getCell(temp.getX(), temp.getY() - 1);
                    // calculating g and h for A* algorithm
                    int g = (abs((up.getX() - startX)) + abs((up.getY() - startY)));
                    int h = (abs((up.getX() - endX)) + abs(up.getY() + endY));
                    // f = the Cell's priority by use of A* algorithm
                    int f = g + h;
                    // enqueue the Cell we want to check, with its priority
                    myQueue.enqueue(up, f);
                    // also add it to the previous cell HashMap
                    previousCell.put(up, temp);
                }
                // RIGHT
                // if the location we want to go is in the make, is visitable and not in the checked set
                if (maze.isLocationInMaze(temp.getX() + 1, temp.getY()) && maze.getCell(temp.getX() + 1, temp.getY()).isVisitable() && !checked.contains(maze.getCell(temp.getX() + 1, temp.getY()))) {
                    Cell right = maze.getCell(temp.getX() + 1, temp.getY());
                    int g = (abs((right.getX() - startX)) + abs((right.getY() - startY)));
                    int h = (abs((right.getX() - endX)) + abs(right.getY() + endY));
                    int f = g + h;
                    myQueue.enqueue(right, f);
                    previousCell.put(right, temp);
                }
                // DOWN
                // if the location we want to go is in the make, is visitable and not in the checked set
                if (maze.isLocationInMaze(temp.getX(), temp.getY() + 1) && maze.getCell(temp.getX(), temp.getY() + 1).isVisitable() && !checked.contains(maze.getCell(temp.getX(), temp.getY() + 1))) {
                    Cell down = maze.getCell(temp.getX(), temp.getY() + 1);
                    int g = (abs((down.getX() - startX)) + abs((down.getY() - startY)));
                    int h = (abs((down.getX() - endX)) + abs(down.getY() + endY));
                    int f = g + h;
                    myQueue.enqueue(down, f);
                    previousCell.put(down, temp);
                }
                // LEFT
                // if the location we want to go is in the make, is visitable and not in the checked set
                if (maze.isLocationInMaze(temp.getX() - 1, temp.getY()) && maze.getCell(temp.getX() - 1, temp.getY()).isVisitable() && !checked.contains(maze.getCell(temp.getX() - 1, temp.getY()))) {
                    Cell left = maze.getCell(temp.getX() - 1, temp.getY());
                    int g = (abs((left.getX() - startX)) + abs((left.getY() - startY)));
                    int h = (abs((left.getX() - endX)) + abs(left.getY() + endY));
                    int f = g + h;
                    myQueue.enqueue(left, f);
                    previousCell.put(left, temp);
                }
            }
        }

        // finalPath will be the set we return
        Set<Cell> finalPath = new HashSet<>();
        // another temp variable that will be used to go through the previousCell HashMap
        Cell tempCell = maze.getEnd();

        // if there was a solution, then do the rest, otherwise, we will return an empty list
        if (previousCell.containsKey(maze.getEnd())) {
            // while we haven't reached back to the start
            while (!tempCell.equals(maze.getStart())) {
                // add the Cell to the finalPath
                finalPath.add(tempCell);
                // then, using the HashMap, get the Cell before it
                tempCell = previousCell.get(tempCell);
            }
            // finally, add the starting cell
            finalPath.add(maze.getStart());
        }

        return finalPath;
    }
}
