package translator.controller;

import translator.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("translator")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping//("/translator")
    public Greeting greeting(@RequestParam(name="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
//    @RequestMapping("/")
//    public Greeting emptyGreeting(@RequestParam(name="name", defaultValue="World") String name) {
//        return new Greeting(counter.incrementAndGet(),
//                "Welcome!");
//    }
}
