package translator.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import translator.dao.TranslationsRepository;
import translator.domain.Translation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    @Value("${yandex.api.key}")
    private  String apiKey;
    @Value("${yandex.api.url}")
    private String apiUrl;
    @Value("${yandex.api.content-type}")
    private String apiContentType;
    @Value("${yandex.api.charset}")
    private String apiCharset;

    @Autowired
    private TranslationsRepository translationsRepository;

    public void save(Translation tn) {
        translationsRepository.save(tn);
    }

    public List<Translation> findAll() {
        return translationsRepository.findAll();
    }

    public String translate(String text, String from, String to) {
        Translation tn = new Translation();
        tn.setTnDate(LocalDate.now());
        tn.setTnTime(LocalTime.now());
        tn.setTnText(text);
        tn.setTnFrom(from);
        tn.setTnTo(to);

        String result = callYandexTranslateApi(text, from, to);

        tn.setTnResult(result);
        translationsRepository.save(tn);

        return result;
    }

    /**
     * Using Yandex Translate API
     *
     * @param text - text to translate
     * @param from - source language
     * @param to   - result language
     * @return - String, that contain translation of each word
     */
    private String callYandexTranslateApi(String text, String from, String to) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String[] words = text.split("\\s");

        try {
            for (int i = 0; i < words.length; i++)
                words[i] = (getResponse(httpClient, fillPostRequest(words[i], from, to))).get("text").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


        return Arrays.stream(words)
                .map(word -> trimBraces(word) + " ")
                .collect(Collectors.joining())
                .trim();
    }

    /**
     * from ["text"] to text
     * @param s result of jsonObject.get("key")
     * @return text without braces
     */
    private String trimBraces(String s) {
        return s.substring(2, s.length()-2);
    }

    private HttpPost fillPostRequest(String word, String from, String to) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(apiUrl + "?lang=" + from + "-" + to + "&key=" + apiKey);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, apiContentType);
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("text", word)), apiCharset));
        return httpPost;
    }

    private JSONObject getResponse(HttpClient httpClient, HttpPost httpPost) throws IOException {
        HttpEntity entity = httpClient
                .execute(httpPost)
                .getEntity();
        return new JSONObject(EntityUtils.toString(entity));
    }
}
