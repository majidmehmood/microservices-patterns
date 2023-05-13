package bulkhead;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RestController
class Service1 {
    final RestTemplate restTemplate;

    Service1(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/svc1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void queryRequest(HttpServletRequest r) {
        System.out.println("started service1");
        Request r1 = new Request(r.getParameter("sleep") != null);
        RequestEntity<Request> requestEntity = RequestEntity.post("http://localhost:8080/svc2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(r1, Map.class);
        restTemplate.exchange(requestEntity, Void.class);
        System.out.println("finished service1");
    }

}
