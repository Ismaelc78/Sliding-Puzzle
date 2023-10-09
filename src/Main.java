import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class  Main {

    public static void main(String[] args) {

        JsonParser data = new JsonParser();
        data.importFile("src/puzzles/problem-1.json");
        long start;
        if(data.validKeys && data.validValues){
            // Backtrack DFS (DATALIST)
            dfs(data);

            // Backtrack Iterative (DATALIST)
            iterativeDFS(data);

            // GraphSearch
            graphSearch(data.goal, data.start);



        }
        else {
            System.out.println("Invalid Keys or Values in JSON file");
        }

    }

    private static void timer(long start, long end){
        long timeSeconds = (end - start) / 1000;
        long mil = (end - start) % 1000;
        long minutes = timeSeconds / 60;
        long seconds = timeSeconds % 60;
        System.out.println("Total time (m:s:ms) = " + minutes + ":" + seconds + ":" + mil) ;
    }

    private static void dfs(JsonParser data){
        long timeStart = System.currentTimeMillis();
        Board dfsBoard = new Board();
        dfsBoard.setBoards(new ArrayList<>(data.goal), new ArrayList<>(data.start));
        dfsBoard.printBoards();
        dfsBoard.n = (int) data.n;
        backtrackingDFS(dfsBoard, "");
        System.out.println("Nodes Examined = " + dfsBoard.nodesExamined);
        timer(timeStart, System.currentTimeMillis());
        while (!dfsBoard.solutionMoves.empty()){
            System.out.println(dfsBoard.solutionMoves.pop());
        }
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

    private static void iterativeDFS(JsonParser data){
        long timeStart = System.currentTimeMillis();
        Board itBoard = new Board();
        itBoard.setBoards(new ArrayList<>(data.goal), new ArrayList<>(data.start));
        itBoard.printBoards();
        itBoard.n = (int) data.n;
        itBoard.depthCount = 0;
        itBoard.nodesExamined = 1;
        boolean solutionFound = false;
        while(!solutionFound){
            System.out.println(itBoard.depthCount + ": Cumulative nodes-examined = " + itBoard.nodesExamined);
            solutionFound = backtrackingDFS(itBoard, "");
            itBoard.depthCount += 1;
        }
        timer(timeStart, System.currentTimeMillis());
        while (!itBoard.solutionMoves.empty()){
            System.out.println(itBoard.solutionMoves.pop());
        }
    }

    private static void graphSearch(List<Integer> goal, List<Integer> start){
        long timeStart = System.currentTimeMillis();
        System.out.println("\n\nGraph Search");
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();
        open.add(new Node(start, "", null));
        int nodesExamined = 0;
        int nodesGenerated = 0;
        while(!open.isEmpty()){
            if(open.get(0).board.getCurrentBoard().equals(goal)){
                System.out.println("Nodes gerenated : " + nodesGenerated);
                System.out.println("Nodes examined : " + nodesExamined);
                timer(timeStart, System.currentTimeMillis());
                printPath(open.get(0));
                break;
            }
            closed.add(open.get(0));
            open.get(0).createSuccessors(open, closed);
            open.addAll(open.get(0).successorBoards);
            nodesGenerated += open.get(0).successorBoards.size();
            open.remove(0);
            if(nodesExamined > 600000){
                break;
            }
            nodesExamined += 1;
        }
    }

    private static void printPath(Node currNode){
        Stack<String> path = new Stack<>();
        int totalMoves = 0;
        while(currNode.parentNode != null){
            path.push(currNode.prevMove);
            currNode = currNode.parentNode;
            totalMoves += 1;
        }
        System.out.println("Solution Length: " + totalMoves);
        while(!path.empty()){
            System.out.println(path.pop());
        }
    }
}

