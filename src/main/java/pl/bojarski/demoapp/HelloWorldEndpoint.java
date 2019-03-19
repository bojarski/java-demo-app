package pl.bojarski.demoapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloWorldEndpoint {

    @RequestMapping(method = GET, path = "hello")
    public String hello() {
        return "Hello heroku World!";
    }
}
