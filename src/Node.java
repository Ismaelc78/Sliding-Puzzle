import java.util.ArrayList;
import java.util.List;


public class Node {
    Board board = new Board();
    List<Node> successorBoards = new ArrayList<>();
    String prevMove;
    Node parentNode;
    int tilesMatched;
    int manhattanDistance;

    int hn;
    public Node(List<Integer> newBoard, List<Integer> goal, String move, Node prevNode){
        board.setBoards(goal, newBoard);
        prevMove = move;
        parentNode = prevNode;
    }

    public void createSuccessors(List<Node> open, List<Node> closed){
        List<String> moves = board.getMovesList(prevMove);
        for (String move : moves){
            board.makeMove(move);
            List<Integer> temp = new ArrayList<>(board.getCurrentBoard());
            board.undoMove(move);
            boolean alreadyClosed = false;
            boolean alreadyOpened = false;
            for(Node node : closed){
                if (node.board.getCurrentBoard().equals(temp)){
                    alreadyClosed = true;
                    break;
                }
            }
            for(Node node : open){
                if (node.board.getCurrentBoard().equals(temp)){
                    alreadyOpened = true;
                    break;
                }
            }
            if (!alreadyClosed && !alreadyOpened){
                successorBoards.add(new Node(temp, board.getGoal(), move, this));
            }
        }
    }

    public void heuristics(){
        for (Integer a : board.getCurrentBoard()){
            int currentPos = board.getCurrentBoard().indexOf(a);
            int goalPos = board.getGoal().indexOf(a);
            if (currentPos == goalPos){
                tilesMatched += 1;
            }
            else {
                int xDiff = Math.abs( (currentPos % board.n) - (goalPos % board.n));
                int yDiff = Math.abs( (currentPos / board.n) - (goalPos / board.n));
                manhattanDistance += (xDiff + yDiff);
            }
            hn = tilesMatched + manhattanDistance;

        }
    }
}
