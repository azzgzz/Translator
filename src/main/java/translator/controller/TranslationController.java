package translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import translator.domain.Translation;
import translator.service.TranslationService;

import java.util.List;

@RestController
@RequestMapping(value = "translate", method = RequestMethod.GET)
public class TranslationController {

    @Autowired
    private TranslationService tnService;

    @RequestMapping
    public String translate(@RequestParam (name ="text") String text,
                             @RequestParam (name = "from") String from,
                             @RequestParam (name = "to") String to) {
        String result = tnService.translate(text, from, to);
        return result;
    }

    @RequestMapping("show_base")
    public List<Translation> showBase() {
        return tnService.findAll();
    }
}
