package translator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import translator.dao.TranslationsRepository;
import translator.domain.Translation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TranslationService {

    @Autowired
    private TranslationsRepository tnRepo;

    public void save(Translation tn){
        tnRepo.save(tn);
    }

    public List<Translation> findAll(){
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

    private String useTranslateApi(String text, String from, String to) {

        return "It is cool translation!";
    }
}
