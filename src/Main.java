import java.util.*;

public class  Main {

    public static void main(String[] args) {

        List<Integer> goal = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> start = Arrays.asList(7, 2, 4, 5, 0, 6, 8, 3, 1);
        List<Integer> start1 = Arrays.asList(7, 2, 4, 5, 0, 6, 8, 3, 1);
        Board dfsBoard = new Board();
        dfsBoard.setBoards(goal, start);
        dfsBoard.printBoards();
        dfsBoard.n = 3;

        backtrackingDFS(dfsBoard, "");
        System.out.println("Nodes Examined = " + dfsBoard.nodesExamined);
        while (!dfsBoard.solutionMoves.empty()){
            System.out.println(dfsBoard.solutionMoves.pop());
        }

        Board itBoard = new Board();
        itBoard.setBoards(goal, start1);
        itBoard.printBoards();
        itBoard.n = 3;
        iterativeDFS(itBoard);
    }

    private static Boolean backtrackingDFS(Board dfsBoard, String prevMove){

        if (dfsBoard.visited()){
            return false;
        }
        else {
            dfsBoard.addToVisitedBoards();
        }
        dfsBoard.nodesExamined += 1;
        if (dfsBoard.count > dfsBoard.depthCount){
            return false;
        }
        if (dfsBoard.boardsMatch()){
            System.out.println("Solution Length = " + dfsBoard.count);
            return true;
        }
        List<String> movesList = dfsBoard.getMovesList(prevMove);
        for (String move : movesList){
            dfsBoard.makeMove(move);
            dfsBoard.count +=1;
            if (backtrackingDFS(dfsBoard, move)) {
                dfsBoard.solutionMoves.push(move);
                return true;
            }
            dfsBoard.undoMove(move);
            dfsBoard.count -=1;
        }
        return false;
    }

    private static void iterativeDFS(Board itBoard){
        itBoard.depthCount = 0;
        itBoard.nodesExamined = 1;
        boolean solutionFound = false;
        while(!solutionFound){
            System.out.println(itBoard.depthCount + ": Cumulative nodes-examined = " + itBoard.nodesExamined);
            solutionFound = backtrackingDFS(itBoard, "");
            itBoard.depthCount += 1;
        }
        while (!itBoard.solutionMoves.empty()){
            System.out.println(itBoard.solutionMoves.pop());
        }
    }
}