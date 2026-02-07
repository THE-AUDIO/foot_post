package foot.ball.foot_api.Match.model;

import lombok.Data;

@Data
public class MatchModel {
    private String id;
    private String title;
    private String category;
    private long date;
    private boolean popular;
    private String poster;
    private TeamsModel teams;
}
