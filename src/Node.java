import java.util.ArrayList;
import java.util.List;



public class Node {
    Board board = new Board();
    List<Node> successorBoards = new ArrayList<>();
    String prevMove;
    Node parentNode;

    public Node(List<Integer> newBoard, String move, Node prevNode){
        board.setCurrentBoard(newBoard);
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
                successorBoards.add(new Node(temp, move, this));
            }
        }
    }
}
