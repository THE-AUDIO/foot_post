package foot.ball.foot_api.generateImage;

import foot.ball.foot_api.Match.ScheduleService;
import foot.ball.foot_api.Match.model.MatchModel;
import foot.ball.foot_api.generateImage.models.ImageGenerateModel;
import foot.ball.foot_api.generateImage.models.InputDto;
import foot.ball.foot_api.generateImage.models.InputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class GenerateImageService {
    @Autowired
    private ScheduleService scheduleService;
    @Value("${key.replica}")
    private  String replicaToken;

    private final   String apiUrl = "https://api.replicate.com/v1/models/google/imagen-4/predictions";

    public ImageGenerateModel PrepareGenerateImage(String club_o, String club_1, long dateOfMatch){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " +this.replicaToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        InputModel body = new InputModel();
        Instant instant = Instant.ofEpochMilli(dateOfMatch);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String dateOfMatchFormated = formatter.format(instant);
        body.setAspectRatio("16:9");
        body.setPrompt(
                "Create a cinematic, high-impact football match poster featuring two opposing clubs.\n" +
                        "\n" +
                        "On the left side, a key player representing " + club_o+ ", wearing the official colors and jersey of " + club_o + ".\n" +
                        "On the right side, a key player representing " + club_o+ ", wearing the official colors and jersey of " + club_o+" .\n" +
                        "\n" +
                        "Both players face each other with intense eye contact, symbolizing rivalry and competition.\n" +
                        "\n" +
                        "Behind them, a large modern stadium at night, filled with a passionate crowd, powerful floodlights cutting through light smoke and atmosphere.\n" +
                        "\n" +
                        "At the center of the composition, a football placed on the pitch, subtly illuminated, acting as the visual focal point.\n" +
                        "\n" +
                        "The club logos of " + club_o + " and " + club_1 + " are clearly visible near each player or integrated subtly into the background design, glowing softly without overpowering the scene.\n" +
                        "\n" +
                        "Color grading is cinematic and dramatic, blending the primary colors of both clubs, with strong contrast and rim lighting on the players.\n" +
                        "\n" +
                        "Clear empty space is reserved for match information (club names, date, time, venue).\n" +
                         "\n" +
                          "le match du match est " + dateOfMatchFormated +" . \n" +
                        "\n" +
                        "Ultra-realistic, photorealistic style, professional sports poster aesthetic, 4K quality, epic, emotional, iconic football match poster."
        );
        body.setSafetyFilterLevel("block_medium_and_above");
        InputDto input = new InputDto();
        input.setInput(body);

        HttpEntity<InputDto> entity = new HttpEntity<>(input, httpHeaders);
        ResponseEntity<ImageGenerateModel> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            entity,
            ImageGenerateModel.class
        );
        return  response.getBody();
    }
    public ImageGenerateModel GenerateImage(){
        List<MatchModel> listMatchOfTomorrow = this.scheduleService.getTomorrowMatch();
        List<MatchModel> matchModels = listMatchOfTomorrow.stream().filter((val)->val.getTitle().contains("Valencia")).toList();
        MatchModel matchModel = matchModels.getFirst();
        String club_1 = matchModel.getTitle().split("vs")[0];
        String club_2 = matchModel.getTitle().split("vs")[1];

     return    this.PrepareGenerateImage(club_1, club_2, matchModel.getDate());
    }


}
