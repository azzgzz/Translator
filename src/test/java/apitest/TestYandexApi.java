package apitest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestYandexApi {

    private static final String apiKey = "trnsl.1.1.20190328T214958Z.754e766f9e6febed.b9f3d0ba088b6977cd73781131a4aa8d563d3b3c";

    @Test
    public void test() {
        String text = "русский язык тоже работает!";
        String lang = "ru-en";
        String charset = "UTF-8";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = null;

        String[] words = text.split("\\s");
        String[] translates = words.clone();

        try {
            for (int i = 0; i < words.length; i++) {

                httpPost = new HttpPost("https://translate.yandex.net/api/v1.5/tr.json/translate?lang=" +
                        lang + "&key=" + apiKey);
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + charset);
                httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("text", words[i])), charset));

                HttpEntity entity = httpClient
                        .execute(httpPost)
                        .getEntity();

                translates[i] = (new JSONObject(EntityUtils.toString(entity))).get("text").toString();
            }

            String result = Arrays.stream(translates)
                    .map(word -> word.replaceAll("[\\]\\[\"]","") + " ")
                    .collect(Collectors.joining()).trim() + "!";
            System.out.println(result);
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
