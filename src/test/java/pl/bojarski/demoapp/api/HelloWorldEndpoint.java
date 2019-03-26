package pl.bojarski.demoapp.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.bojarski.demoapp.DemoappApplicationTests;

public class HelloWorldEndpoint extends DemoappApplicationTests {

    @Test
    public void shouldReturnGreetings() {
        //given
        final String url = "http://localhost:" + port + "/hello";

        //when
        ResponseEntity<String> response = httpClient.getForEntity(url, String.class);

        //then
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isEqualTo("Hello heroku World!");
    }
}
