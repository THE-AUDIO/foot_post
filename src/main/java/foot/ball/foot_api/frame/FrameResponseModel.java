package foot.ball.foot_api.frame;

import lombok.Data;

@Data
public class FrameResponseModel {
    private String task_id;
    private String task_type;
    private  String original_image_url;
    private String image_url;
}
