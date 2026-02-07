package foot.ball.foot_api.generateImage;

import foot.ball.foot_api.generateImage.models.ImageGenerateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateImageController {
    @Autowired
    private GenerateImageService generateImageService;

    @PostMapping("/create")
    public ImageGenerateModel GenerateImageForMatch(){
        return  this.generateImageService.GenerateImage();
    }

}
