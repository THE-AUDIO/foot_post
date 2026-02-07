package foot.ball.foot_api.Match.model;

import lombok.Data;

import java.util.List;

@Data
public class MatchDto {
    private boolean success;
    private List<MatchModel> data;
}
