package bookstore.laidu.org.bookstore.uitl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HttpUtil {
    public static String getJsonData(String url) throws Exception {

        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();

        if (httpEntity != null) {
            InputStream instream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        return null;
    }

    public static List<Map<String, Object>> getData(String url, String keys) {
        String[] keydata = keys.split(",");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();

        /**
         * 这里需要分析服务器回传的json格式数据，
         */
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(HttpUtil.getJsonData(url))
                    .getJSONObject("result");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                for (int j = 0; j < keydata.length; j++) {
                    map.put(keydata[j], jsonObject2.getString(keydata[j]));
                }
                list.add(map);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return list;
    }


}
