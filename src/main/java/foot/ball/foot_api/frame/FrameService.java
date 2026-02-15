package foot.ball.foot_api.frame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;


@Service
public class FrameService {
    final String apiUrl= "https://api.apiframe.ai/pro/imagine";
    @Value("${key.frame_api_key}")
    private  String api_key;

    public String GenerateImage(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization",  api_key);
        BodyDto bodyDto = new BodyDto();
        bodyDto.setModel("flux-pro");
        bodyDto.setPrompt("a sunflower field in the wind");
        HttpEntity<BodyDto> entity = new HttpEntity<>(bodyDto, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );
        return  response.getBody();
    }
}
