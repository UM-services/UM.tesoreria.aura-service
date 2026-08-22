package um.tesoreria.aura.helloTest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tesoreria/aura/hello")
public class HelloTestController {

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

}
