package unibs.ing.progettosw.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONUtilities {
    public Map<String, Integer> objectToStrIntMap(JSONObject array) {
        Map<String, Integer> map = new HashMap<>();

        Iterator<?> keys = array.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            int value = array.getInt(key);
            map.put(key, value);
        }

        return map;
    }

    public Map<String, Integer> JSONArrayToStrIntMap(JSONArray array) {
        Map<String, Integer> map = new HashMap<>();

        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                //Adding each element of JSON array into ArrayList
                JSONObject objectMap = (JSONObject) array.get(i);
                String key = objectMap.getString("key");
                int value = objectMap.getInt("value");
                map.put(key, value);
            }
        }

        return map;
    }
}
