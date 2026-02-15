package foot.ball.foot_api.generateImage;

import foot.ball.foot_api.Match.ScheduleService;
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
                "Create an ultra-realistic, modern professional football match poster in a 2025 sports photography style.\n" +

                        "Two elite professional football players face each other in a dramatic head-to-head composition.\n" +

                        "On the left: a recent key player representing " + club_o + ", wearing the official 2024–2025 home jersey of " + club_o +
                        ", authentic fabric texture, realistic sponsor logos, natural folds and stitching.\n" +

                        "On the right: a recent key player representing " + club_1 + ", wearing the official 2024–2025 home jersey of " + club_1 +
                        ", same level of realism and detail.\n" +

                        "Both players have intense, focused facial expressions, realistic skin texture, sweat, natural lighting, no exaggeration, no cartoon effect.\n" +

                        "Background: a large modern football stadium at night, fully packed crowd, realistic fans, powerful LED floodlights, subtle atmospheric smoke, shallow depth of field.\n" +

                        "Lighting: cinematic rim light outlining the players, realistic shadows, high dynamic range, professional sports broadcast lighting.\n" +

                        "Center foreground: a realistic professional football resting on the pitch, sharp focus, slightly illuminated.\n" +

                        "Club logos of " + club_o + " and " + club_1 + " integrated naturally into the background design, subtle glow, not oversized.\n" +

                        "Color grading: modern sports poster look, deep contrast, realistic colors, no oversaturation, cinematic tone.\n" +

                        "Typography space: leave clean empty space for match details (club names, date, time, venue).\n" +

                        "Match date displayed clearly on the poster: " + dateOfMatchFormated + ".\n" +

                        "Style constraints: photorealistic, professional sports photography, 4K quality, sharp focus, realistic proportions.\n" +

                        "Negative style: no illustration, no cartoon, no anime, no fantasy, no distorted faces, no extra limbs, no blur, no low quality."
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
}
