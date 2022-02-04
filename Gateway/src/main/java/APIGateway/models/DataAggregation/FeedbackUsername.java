package APIGateway.models.DataAggregation;

import APIGateway.models.FilmApplication.Feedback;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.*;

public class FeedbackUsername {
    private final String comment;
    private final float score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private final Date date;
    private final String username;

    public FeedbackUsername(Feedback f, List<LinkedHashMap<String, String>> users) {
        comment = f.getComment();
        score = f.getScore();
        date = f.getDate();

        for (Map u : users){
            if ((int) u.get("id_user") == f.getId_user()){
                username = (String) u.get("username");
                return;
            }
        }
        throw new RuntimeException();
    }

    public String getComment() {
        return comment;
    }

    public float getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }
}
