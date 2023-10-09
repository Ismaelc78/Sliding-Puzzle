import java.util.Comparator;

public class SortNodes implements Comparator<Node> {
    public int compare(Node a, Node b){
        return a.hn - b.hn;
    }
}
