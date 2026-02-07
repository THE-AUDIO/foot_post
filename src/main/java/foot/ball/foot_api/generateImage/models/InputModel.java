package foot.ball.foot_api.generateImage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InputModel {
    @JsonProperty("aspect_ratio")
    private String aspectRatio;
    private String prompt;
    @JsonProperty("safety_filter_level")
    private String safetyFilterLevel;
}
