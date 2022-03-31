package dk.kvalitetsit.hello.controller;

import dk.kvalitetsit.hello.service.TrivyService;
import dk.kvalitetsit.hello.trivy.Response;
import dk.kvalitetsit.hello.trivy.Trivy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrivyReceiverController {
    private static final Logger logger = LoggerFactory.getLogger(TrivyReceiverController.class);
    private final TrivyService helloService;
    private final String apiKey;

    public TrivyReceiverController(TrivyService helloService, @Value("${KEY}") String apiKey) {
        this.helloService = helloService;
        this.apiKey = apiKey;
    }

    @PostMapping("/receive")
    public ResponseEntity<Response> v1HelloPost(@RequestBody Trivy trivy, @RequestHeader(name = "X-KEY") String apiKey) {
        logger.debug("Enter POST hello.");
        if(!apiKey.equals(this.apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        helloService.helloServiceBusinessLogic(trivy);

        var response = new Response();
        response.setStatus("OK");
        return ResponseEntity.ok(response);
    }
}
