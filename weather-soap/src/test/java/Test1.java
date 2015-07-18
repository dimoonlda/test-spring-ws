import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lutay.d on 18.07.2015.
 */
public class Test1 {

    private static final Gson GSON = new Gson();
    @Test
    public void testJsonToString(){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Identifier", "123456789");
        data.put("Description", "need block");
        data.put("user", "dimoon");
        String mapAsJson = GSON.toJson(data);
        System.out.println(mapAsJson);

        Map<String, Object> responseData =
                GSON.fromJson(mapAsJson, HashMap.class);
        responseData.keySet().stream().forEach(System.out::println);
    }
}
