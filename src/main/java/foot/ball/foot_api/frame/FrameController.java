package foot.ball.foot_api.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrameController {
    @Autowired
    private FrameService frameService;

    @GetMapping("/frame")
    public String GenerateImageFromFrame(){
        return  this.frameService.GenerateImage();
    }
}
