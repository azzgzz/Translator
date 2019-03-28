package translator.controller;

import translator.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController("/")
public class WelcomeController {

    private static final String template = "Welcome, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping//("/translator")
    public Greeting greeting(@RequestParam(name="name", defaultValue="Guest") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
