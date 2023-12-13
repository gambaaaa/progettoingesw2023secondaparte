package unibs.ing.progettosw.utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class JSONFile {

    protected JSONObject readFromJSON(String path) {
        JSONObject object;
        InputStream is = FileService.class.getResourceAsStream(path);
        JSONTokener tokener = new JSONTokener(is);
        object = new JSONObject(tokener);

        return object;
    }
}
