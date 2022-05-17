import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Arrays;

public class testing {
    public static void main(String[] args) throws IOException {
        String[] letters = new String[] {"a", "b", "c"};
        int i = indexOf(letters, "a");
        System.out.println(i);


    }
    private static int indexOf(Object[] strArray, Object element){

        /*
         * Convert array to List and then
         * use indexOf method of List class.
         */
        int index = Arrays.asList(strArray).indexOf(element);

        return index;

    }
}
