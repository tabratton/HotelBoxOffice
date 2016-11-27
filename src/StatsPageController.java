/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.util.HashMap;
import java.util.Map;


/**
 * FXML Controller class
 *
 * @author Chad
 */
public class StatsPageController implements Initializable {

    @FXML
    private Label WatchedMovie1;
    @FXML
    private Label WatchedMovie2;
    @FXML
    private Label WatchedMovie3;
    @FXML
    private Label WatchedMovie4;
    @FXML
    private Label WatchedMovie5;
    @FXML
    private Label SearchTerm1;
    @FXML
    private Label SearchTerm2;
    @FXML
    private Label SearchTerm3;
    @FXML
    private Label SearchTerm4;
    @FXML
    private Label SearchTerm5;
    @FXML
    private Label ViewedMovie1;
    @FXML
    private Label ViewedMovie2;
    @FXML
    private Label ViewedMovie3;
    @FXML
    private Label ViewedMovie4;
    @FXML
    private Label ViewedMovie5;
    @FXML
    private Label RatedMovie1;
    @FXML
    private Label RatedMovie2;
    @FXML
    private Label RatedMovie3;
    @FXML
    private Label RatedMovie4;
    @FXML
    private Label RatedMovie5;
    @FXML
    private Label BottomRated1;
    @FXML
    private Label BottomRated2;
    @FXML
    private Label BottomRated3;
    @FXML
    private Label BottomRated4;
    @FXML
    private Label BottomRated5;
    @FXML
    private Label ViewedActor1;
    @FXML
    private Label ViewedActor2;
    @FXML
    private Label ViewedActor3;
    @FXML
    private Label ViewedActor4;
    @FXML
    private Label ViewedActor5;

    private final Map actor= new HashMap();
    private final Map movie= new HashMap();
//    private Map<Integer, String[]> rating = new HashMap<Integer, String[]>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ResultSet actors = HotelBox.dbConnection.searchStatement("ACTORS");
        ResultSet movies = HotelBox.dbConnection.searchStatement("MOVIES");
        String sqlStatement;
        try {
                   
            while(actors.next()){
                actor.put(actors.getInt("ACTOR_ID"), actors.getString("ACTOR_NAME"));
            }
            while (movies.next()){
                movie.put(movies.getInt("MOVIE_ID"), movies.getString("MOVIE_TITLE"));
            }
            
            // put top 5 search terms into their respective labels
            sqlStatement = String.format("SELECT * FROM SEARCH_TERMS ORDER BY FREQUENCY DESC LIMIT 5");
            ResultSet searches = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 0; i < 5; i++){
                searches.next();
                switch (i){
                    case 0:
                        SearchTerm1.setText(searches.getString("TERM") + " (" + searches.getInt("FREQUENCY") + ")");
                        break;
                    case 1:
                        SearchTerm2.setText(searches.getString("TERM") + " (" + searches.getInt("FREQUENCY") + ")");
                        break;
                    case 2:
                        SearchTerm3.setText(searches.getString("TERM") + " (" + searches.getInt("FREQUENCY") + ")");
                        break;
                    case 3:
                        SearchTerm4.setText(searches.getString("TERM") + " (" + searches.getInt("FREQUENCY") + ")");
                        break;
                    case 4:
                        SearchTerm5.setText(searches.getString("TERM") + " (" + searches.getInt("FREQUENCY") + ")");
                        break;
                    default:
                        break;
                }
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            
            sqlStatement = String.format("SELECT * FROM MOVIES ORDER BY TIMES_VIEWED DESC LIMIT 5");
            ResultSet lookedMovie = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 1; i <= 5; i++){
                lookedMovie.next();
                switch (i){
                    case 1:
                        ViewedMovie1.setText(lookedMovie.getString("MOVIE_TITLE") + " (" + lookedMovie.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 2:
                        ViewedMovie2.setText(lookedMovie.getString("MOVIE_TITLE") + " (" + lookedMovie.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 3:
                        ViewedMovie3.setText(lookedMovie.getString("MOVIE_TITLE") + " (" + lookedMovie.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 4:
                        ViewedMovie4.setText(lookedMovie.getString("MOVIE_TITLE") + " (" + lookedMovie.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 5:
                        ViewedMovie5.setText(lookedMovie.getString("MOVIE_TITLE") + " (" + lookedMovie.getInt("TIMES_VIEWED") + ")");
                        break;
                    default:
                        break;
                }
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            sqlStatement = String.format("SELECT * FROM ACTORS ORDER BY TIMES_VIEWED DESC LIMIT 5");
            ResultSet viewedActors = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 1; i <= 5; i++){
                viewedActors.next();
                switch (i){
                    case 1:
                        ViewedActor1.setText(viewedActors.getString("ACTOR_NAME") + " (" + viewedActors.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 2:
                        ViewedActor2.setText(viewedActors.getString("ACTOR_NAME") + " (" + viewedActors.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 3:
                        ViewedActor3.setText(viewedActors.getString("ACTOR_NAME") + " (" + viewedActors.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 4:
                        ViewedActor4.setText(viewedActors.getString("ACTOR_NAME") + " (" + viewedActors.getInt("TIMES_VIEWED") + ")");
                        break;
                    case 5:
                        ViewedActor5.setText(viewedActors.getString("ACTOR_NAME") + " (" + viewedActors.getInt("TIMES_VIEWED") + ")");
                        break;
                    default:
                        break;
                }
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            sqlStatement = String.format("SELECT MOVIE_ID, COUNT(MOVIE_ID) as 'total' FROM CUSTOMER_RENTALS GROUP BY MOVIE_ID ORDER BY COUNT(MOVIE_ID) DESC LIMIT 5");
            ResultSet uniqueWatched = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 1; i <= 5; i++){
                uniqueWatched.next();
                int movieID = Integer.valueOf(uniqueWatched.getString("MOVIE_ID"));
                switch (i){
                    case 1:
                        WatchedMovie1.setText(movie.get(movieID) + " (" + uniqueWatched.getInt("total") + ")");
                        break;
                    case 2:
                        WatchedMovie2.setText(movie.get(movieID) + " (" + uniqueWatched.getInt("total") + ")");
                        break;
                    case 3:
                        WatchedMovie3.setText(movie.get(movieID) + " (" + uniqueWatched.getInt("total") + ")");
                        break;
                    case 4:
                        WatchedMovie4.setText(movie.get(movieID) + " (" + uniqueWatched.getInt("total") + ")");
                        break;
                    case 5:
                        WatchedMovie5.setText(movie.get(movieID) + " (" + uniqueWatched.getInt("total") + ")");
                        break;
                    default:
                        break;
                }
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
            
        try {    
            // get ratings to put in their respective labels
            sqlStatement = String.format("SELECT MOVIE_ID, COUNT(*) as 'number', SUM(RATING_NUM) as 'total' FROM RATING GROUP BY MOVIE_ID ORDER BY 'total' DESC LIMIT 5");
            ResultSet uniqueRating = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 1; i <= 5; i++){
                uniqueRating.next();
                int movieID = Integer.valueOf(uniqueRating.getString("MOVIE_ID"));
                float avgRating = (float) (uniqueRating.getInt("total")) / uniqueRating.getInt("number");
                switch (i){
                    case 1:
                        RatedMovie1.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 2:
                        RatedMovie2.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 3:
                        RatedMovie3.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 4:
                        RatedMovie4.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 5:
                        RatedMovie5.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    default:
                        break;
                }
            }
        
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
        try{
            // get ratings to put in their respective labels
            sqlStatement = String.format("SELECT MOVIE_ID, COUNT(*) as 'number', SUM(RATING_NUM) as 'total' FROM RATING GROUP BY MOVIE_ID ORDER BY 'total' ASC LIMIT 5");
            ResultSet bottomRating = HotelBox.dbConnection.searchStatement(sqlStatement, true);
            for (int i = 1; i <= 5; i++){
                bottomRating.next();
                int movieID = Integer.valueOf(bottomRating.getString("MOVIE_ID"));
                float avgRating = (float) (bottomRating.getInt("total")) / bottomRating.getInt("number");
                switch (i){
                    case 1:
                        BottomRated1.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 2:
                        BottomRated2.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 3:
                        BottomRated3.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 4:
                        BottomRated4.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    case 5:
                        BottomRated5.setText(movie.get(movieID) + " (" + avgRating + ")");
                        break;
                    default:
                        break;
                }
            }
     
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
        
        
        
        
    }    
    
}
