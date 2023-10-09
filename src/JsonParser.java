import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public List<Integer> goal = new ArrayList<>();
    public List<Integer> start = new ArrayList<>();
    public long n;

    public boolean validKeys = false;
    public boolean validValues = false;
    public void importFile(String filePath) {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.File not found");
            return;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jo = (JSONObject) obj;
        validKeys = checkKeys(jo);
        getValues(jo);
        validValues = checkValues(jo);
    }

    private void getValues(JSONObject jo){
        n = (long) jo.get("n");
        getPuzzleBoards(start, "start", jo);
        getPuzzleBoards(goal, "goal", jo);
    }
    private void getPuzzleBoards(List<Integer> board, String boardName, JSONObject jo){
        JSONArray list = (JSONArray) jo.get(boardName);
        for (Object o : list) {
            JSONArray m = (JSONArray) o;
            for (Object val : m) {
                Long i = (Long) val;
                board.add(i.intValue());
            }
        }
    }

    private boolean checkKeys(JSONObject jo){
        return jo.containsKey("n") || jo.containsKey("start") || jo.containsKey("goal");
    }
    private boolean checkValues(JSONObject jo){
        if (n < 2 || goal.size() != n*n || start.size() != n*n){
            return false;
        }
        for (int i = 0; i < n ; i++){
            if(!start.contains(i) || !goal.contains(i)){
                return false;
            }
        }
        return true;
    }
}
