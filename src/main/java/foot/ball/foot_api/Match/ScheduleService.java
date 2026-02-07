package foot.ball.foot_api.Match;

import foot.ball.foot_api.Match.model.MatchDto;
import foot.ball.foot_api.Match.model.MatchModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

      final  String apiUrl = "https://api.sportsrc.org/?data=matches&category=football";



    public MatchDto getListMatch(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MatchDto> entity = new HttpEntity<>(headers);
        ResponseEntity<MatchDto> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                MatchDto.class
        );
        return response.getBody();
    }

    public List<MatchModel> getPopularMatch(){
        return this.getListMatch().getData().stream().filter(MatchModel::isPopular).toList();
    }

    public List<MatchModel> getTomorrowMatch() {
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);

        return this.getPopularMatch().stream()
                .filter(match -> {
                    Instant matchInstant = Instant.ofEpochMilli(match.getDate());
                    LocalDate matchDate = matchInstant.atZone(ZoneId.systemDefault()).toLocalDate();
                    return matchDate.equals(tomorrowDate);
                })
                .collect(Collectors.toList());
    }

}
