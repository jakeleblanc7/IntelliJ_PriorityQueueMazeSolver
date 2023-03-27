import maze.Cell;
import maze.Maze;
import maze.MazeRenderer;
import maze.MazeSolver;

import java.util.Set;

public class Asn3 {
    private static final String RELATIVE_RESOURCES = "./resources/maze/";

    public static void main(String[] args) {

        // making a maze solver called mySolver
        AStarMazeSolver mySolver = new AStarMazeSolver();

        // making a new maze renderer called myRenderer
        MazeRenderer myRenderer = new MazeRenderer();

        // printing out the rendered maze and using the AStarMazeSolver to solve that maze for each of the 3 provided mazes
        System.out.println(myRenderer.render(Maze.fromFile(RELATIVE_RESOURCES + "maze1.txt"), mySolver.solve(Maze.fromFile(RELATIVE_RESOURCES + "maze1.txt"))));
        System.out.println(myRenderer.render(Maze.fromFile(RELATIVE_RESOURCES + "maze2.txt"), mySolver.solve(Maze.fromFile(RELATIVE_RESOURCES + "maze2.txt"))));
        System.out.println(myRenderer.render(Maze.fromFile(RELATIVE_RESOURCES + "maze3.txt"), mySolver.solve(Maze.fromFile(RELATIVE_RESOURCES + "maze3.txt"))));

    }
}
