package foot.ball.foot_api.generateImage.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Data
public class ImageGenerateModel {
    private String id;
    private String model;
    private String version;
    private InputModel input;
    private String logs;
    private Object output;
    private boolean dataRemoved;
    private Object error;
    private String source;
    private String status;
    @JsonProperty("created_at")
    private Instant createdAt;
    private UrlsModel urls;
}
