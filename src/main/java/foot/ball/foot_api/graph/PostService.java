package foot.ball.foot_api.graph;

import foot.ball.foot_api.tools.SendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {
    @Value("${key.graph}")
    private String apiGraph;

    public Optional<ResponseEntity<String>> newPost(String title, String imageUrl){
        Map<String, String> body = new HashMap<>();
        body.put("caption",title);
        body.put("url",imageUrl);
        body.put("access_token",apiGraph);
        HttpHeaders httpHeaders = new HttpHeaders();
        String apiUrl = "https://graph.facebook.com/v24.0/939684432569675/photos";
        SendRequest NewRequest = new SendRequest(body,null, apiUrl,httpHeaders);
        return   NewRequest.sendPostRequest();
    }

}
