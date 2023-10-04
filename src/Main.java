import java.util.*;

public class  Main {

    static String UP = "up";
    static String DOWN = "down";
    static String LEFT = "left";
    static String RIGHT = "right";
    static int n = 3;
    static Integer nodesExamined = 0;
    static HashMap<List<Integer>, Integer> visitedBoards = new HashMap<>();
    static Stack<String> solutionMoves = new Stack<String>();
    static Integer depthCount = 26;
    public static void main(String[] args) {

        List<Integer> goal = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> recursStart = Arrays.asList(7, 2, 4, 5, 0, 6, 8, 3, 1);;
        List<Integer> itStart = Arrays.asList(7, 2, 4, 5, 0, 6, 8, 3, 1);;
        int count = 0;
        printBoard(recursStart, "Start");
        printBoard(goal, "Goal");
        Boolean solutionFound = backtrackingDFS(recursStart, goal, "", count);
        System.out.println("Nodes Examined = " + nodesExamined);
        while (!solutionMoves.empty()){
            System.out.println(solutionMoves.pop());
        }
        iterativeDFS(itStart, goal);
    }

    private static void printBoard(List<Integer> board, String boardName){
        System.out.println("\n" + boardName + ":");
        for (int i = 0; i < board.size(); i++){
            if( i % n == 0 && i != 0){
                System.out.println();
            }
            System.out.print(board.get(i) + " ");
        }
        System.out.println();

    }

    private static List<String> getMovesList(List<Integer> board, String prevMove){
        List<String> validMoves = new ArrayList<String>();
        int indexOfZero = board.indexOf(0);

        if (!Objects.equals(prevMove, RIGHT) && indexOfZero % n > 0 ){
            validMoves.add(LEFT);
        }
        if (!Objects.equals(prevMove, LEFT) && indexOfZero % n < n-1){
            validMoves.add(RIGHT);
        }
        if (!Objects.equals(prevMove, DOWN) && indexOfZero >= n){
            validMoves.add(UP);
        }
        if (!Objects.equals(prevMove, UP) && indexOfZero < board.size() - n){
            validMoves.add(DOWN);
        }

        return validMoves;
    }

    private static void makeMove(List<Integer> board, String move){
        int indexOfZero = board.indexOf(0);

        if (Objects.equals(move, UP)){
            board.set(indexOfZero, board.get(indexOfZero - n));
            board.set(indexOfZero-n, 0);
        } else if (Objects.equals(move, DOWN)) {
            board.set(indexOfZero, board.get(indexOfZero + n));
            board.set(indexOfZero+n, 0);
        } else if (Objects.equals(move, RIGHT)) {
            board.set(indexOfZero, board.get(indexOfZero+1));
            board.set(indexOfZero+1, 0);
        } else if (Objects.equals(move, LEFT)){
            board.set(indexOfZero, board.get(indexOfZero-1));
            board.set(indexOfZero-1, 0);
        }
    }

    private static void undoMove(List<Integer> board, String move){
        if (Objects.equals(move, UP)){
            makeMove(board, DOWN);
        } else if (Objects.equals(move, DOWN)) {
            makeMove(board, UP);
        } else if (Objects.equals(move, RIGHT)) {
            makeMove(board, LEFT);
        } else if (Objects.equals(move, LEFT)){
            makeMove(board, RIGHT);
        }
    }

    private static Boolean backtrackingDFS(List<Integer> board, List<Integer> goal, String prevMove, int count){

        if (visitedBoards.containsKey(board) && visitedBoards.get(board) < count){
            return false;
        }
        else {
            visitedBoards.put(board, count);
        }
        nodesExamined += 1;
        if (count > depthCount){
            return false;
        }
        if (board.equals(goal)){
            System.out.println("Solution Length = " + count);
            return true;
        }
        List<String> movesList = getMovesList(board, prevMove);
        for (String move : movesList){
            makeMove(board, move);
            count +=1;
            if (backtrackingDFS(board, goal, move, count)) {
                solutionMoves.push(move);
                return true;
            }
            undoMove(board, move);
            count -=1;
        }
        return false;
    }

    private static void iterativeDFS(List<Integer> board, List<Integer> goal){
        depthCount = 0;
        nodesExamined = 1;
        boolean solutionFound = false;
        while(!solutionFound){
            System.out.println(depthCount + ": Cumulative nodes-examined = " + nodesExamined);
            solutionFound = backtrackingDFS(board, goal, "", 0);
            depthCount += 1;
        }
        while (!solutionMoves.empty()){
            System.out.println(solutionMoves.pop());
        }
    }
}