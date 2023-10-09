import java.util.*;

public class Board {

    private final HashMap<List<Integer>, Integer> visitedBoards = new HashMap<>();
    private final String UP = "up";
    private final String DOWN = "down";
    private final String LEFT = "left";
    private final String RIGHT = "right";
    private List<Integer> goal;
    private List<Integer> currentBoard;
    int n = 3;
    Integer nodesExamined = 0;
    Stack<String> solutionMoves = new Stack<>();
    Integer depthCount = 26;
    int count = 0;

    public boolean boardsMatch(){
        return currentBoard.equals(goal);
    }
    public void setBoards(List<Integer> goalBoard, List<Integer> startBoard){
        goal = goalBoard;
        currentBoard = startBoard;
    }

    public void setCurrentBoard(List<Integer> board){
        currentBoard = board;
    }
    public void printBoards(){
        printBoard(currentBoard, "Start");
        printBoard(goal, "Goal");
    }

    private void printBoard(List<Integer> board, String name){
        System.out.println("\n" + name + ":");
        for (int i = 0; i < board.size(); i++){
            if( i % n == 0 && i != 0){
                System.out.println();
            }
            System.out.print(board.get(i) + " ");
        }
        System.out.println();
    }

    public void makeMove(String move){
        int indexOfZero = currentBoard.indexOf(0);

        if (Objects.equals(move, UP)){
            currentBoard.set(indexOfZero, currentBoard.get(indexOfZero - n));
            currentBoard.set(indexOfZero-n, 0);
        } else if (Objects.equals(move, DOWN)) {
            currentBoard.set(indexOfZero, currentBoard.get(indexOfZero + n));
            currentBoard.set(indexOfZero+n, 0);
        } else if (Objects.equals(move, RIGHT)) {
            currentBoard.set(indexOfZero, currentBoard.get(indexOfZero+1));
            currentBoard.set(indexOfZero+1, 0);
        } else if (Objects.equals(move, LEFT)){
            currentBoard.set(indexOfZero, currentBoard.get(indexOfZero-1));
            currentBoard.set(indexOfZero-1, 0);
        }
    }

    public void undoMove(String move){
        if (Objects.equals(move, UP)){
            makeMove(DOWN);
        } else if (Objects.equals(move, DOWN)) {
            makeMove(UP);
        } else if (Objects.equals(move, RIGHT)) {
            makeMove(LEFT);
        } else if (Objects.equals(move, LEFT)){
            makeMove(RIGHT);
        }
    }

    /***
     * Rules to check against
     * @param prevMove -
     * @return validMoves - List<String>
     */
    public List<String> getMovesList(String prevMove){
        List<String> validMoves = new ArrayList<>();
        int indexOfZero = currentBoard.indexOf(0);

        if (!Objects.equals(prevMove, RIGHT) && indexOfZero % n > 0 ){
            validMoves.add(LEFT);
        }
        if (!Objects.equals(prevMove, LEFT) && indexOfZero % n < n-1){
            validMoves.add(RIGHT);
        }
        if (!Objects.equals(prevMove, DOWN) && indexOfZero >= n){
            validMoves.add(UP);
        }
        if (!Objects.equals(prevMove, UP) && indexOfZero < currentBoard.size() - n){
            validMoves.add(DOWN);
        }
        return validMoves;
    }

    /***
     * To keep track of boards that have
     * been visited
     * @return bool
     */
    public boolean visited(){
        return (visitedBoards.containsKey(currentBoard) && visitedBoards.get(currentBoard) < count);
    }

    public void addToVisitedBoards(){
        visitedBoards.put(currentBoard, count);
    }

    public List<Integer> getCurrentBoard(){
        return currentBoard;
    }

    public List<Integer> getGoal(){
        return goal;
    }
}
