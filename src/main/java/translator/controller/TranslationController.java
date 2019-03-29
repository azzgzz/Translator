package translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import translator.domain.Translation;
import translator.service.TranslationService;

import java.util.List;

@RestController
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @RequestMapping("/")
    public String translate(@RequestParam(name = "text") String text,
                            @RequestParam(name = "from") String from,
                            @RequestParam(name = "to") String to) {
        System.out.println("Принял запрос на перевод с ");
        return translationService.translate(text, from, to);

    }

    @GetMapping(value = "/{from}/{to}/{text}")
    public String translate2(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("text") String text) {
        return translationService.translate(text, from, to);

    }

    @RequestMapping("/history")
    public List<Translation> showBase() {
        return translationService.findAll();
    }
}
