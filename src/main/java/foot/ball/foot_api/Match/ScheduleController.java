package foot.ball.foot_api.Match;

import foot.ball.foot_api.Match.model.MatchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/schedule")
    public List<MatchModel> getListMatch(){
        return  this.scheduleService.getPopularMatch();
    }
    @GetMapping("/tomorrow")
    public  List<MatchModel> getTomorrowMatch(){
        return  this.scheduleService.getTomorrowMatch();
    }
}
