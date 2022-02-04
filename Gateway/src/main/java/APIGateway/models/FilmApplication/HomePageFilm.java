package APIGateway.models.FilmApplication;

import APIGateway.EnumGenre;

import java.util.List;


public class HomePageFilm extends BaseFilm {

    private List<Actor> actors;
    private float avgScore;

    public HomePageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster, List<Actor> actors, float avgScore) {
        super(id, title, genre, plot, trailer, poster);
        this.actors = actors;
        this.avgScore = avgScore;
    }

    public HomePageFilm() {

    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }
}
