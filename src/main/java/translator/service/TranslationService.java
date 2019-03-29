package translator.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String apiKey = "trnsl.1.1.20190328T214958Z.754e766f9e6febed.b9f3d0ba088b6977cd73781131a4aa8d563d3b3c";
    private static final String charset = "UTF-8";

    @Autowired
    private TranslationsRepository tnRepo;

    public void save(Translation tn) {
        tnRepo.save(tn);
    }

    public List<Translation> findAll() {
        return tnRepo.findAll();
    }

    public String translate(String text, String from, String to) {
        Translation tn = new Translation();
        tn.setTnDate(LocalDate.now());
        tn.setTnTime(LocalTime.now());
        tn.setTnText(text);
        tn.setTnFrom(from);
        tn.setTnTo(to);

        String result = useTranslateApi(text, from, to);

        tn.setTnResult(result);
        tnRepo.save(tn);

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
    private String useTranslateApi(String text, String from, String to) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = null;

        String[] words = text.split("\\s");
        String result = "";

        try {
            for (int i = 0; i < words.length; i++) {
                httpPost = new HttpPost("https://translate.yandex.net/api/v1.5/tr.json/translate?lang=" +
                        from + "-" + to + "&key=" + apiKey);
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + charset);
                httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("text", words[i])), charset));
                HttpEntity entity = httpClient
                        .execute(httpPost)
                        .getEntity();
                words[i] = (new JSONObject(EntityUtils.toString(entity))).get("text").toString();
            }

            result = Arrays.stream(words)
                    .map(word -> word.replaceAll("[]\\[\"]", "") + " ")
                    .collect(Collectors.joining()).trim();
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return result;
    }
}
