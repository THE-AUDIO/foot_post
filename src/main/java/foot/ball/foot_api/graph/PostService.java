package foot.ball.foot_api.graph;

import foot.ball.foot_api.Match.model.MatchModel;
import foot.ball.foot_api.tools.SendRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {
    @Value("${key.graph}")
    private String apiGraph;

    public Optional<ResponseEntity<String>> newPost(MatchModel match, String imageUrl){
        Map<String, String> body = new HashMap<>();
        Instant instant = Instant.ofEpochMilli(match.getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String dateOfMatchFormated = formatter.format(instant);
        String hashtags = "#Football #Match #Tomorrow";
        body.put("caption",match.getTitle() + " \n" + dateOfMatchFormated + " \n" + hashtags);
        body.put("url",imageUrl);
        body.put("access_token",apiGraph);
        HttpHeaders httpHeaders = new HttpHeaders();
        String apiUrl = "https://graph.facebook.com/v24.0/939684432569675/photos";
        SendRequest NewRequest = new SendRequest(body,null, apiUrl,httpHeaders);
        return   NewRequest.sendPostRequest();
    }

}
