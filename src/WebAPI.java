import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class WebAPI {
    public static void getNowPlaying() {
        Scanner scan = new Scanner(System.in);
        String APIkey = "fd6053275bbd213b53983477b41aa7f3"; // your personal API key on TheMovieDatabase
        String queryParameters = "?api_key=" + APIkey;
        String endpoint = "https://api.themoviedb.org/3/movie/now_playing";
        String url = endpoint + queryParameters;
        String urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // when determining HOW to parse the returned JSON data,
        // first, print out the urlResponse, then copy/paste the output
        // into the online JSON parser: https://jsonformatter.org/json-parser
        // use the visual model to help you determine how to parse the data!
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray movieList = jsonObj.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            System.out.println(movieID + " " + movieTitle + " " + fullPosterPath);
        }

        System.out.println("");
        System.out.print("Enter a movie ID to learn more: ");
        int temp = scan.nextInt();
        scan.nextLine();
        String endpoint2 = "https://api.themoviedb.org/3/movie/" + temp;
        String url2 = endpoint2 + queryParameters;
        String urlResponse2 = "";
        try {
            URI myUri = URI.create(url2); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse2 = response.body();
            System.out.println(urlResponse2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JSONObject jsonObj2 = new JSONObject(urlResponse2);
        String title = jsonObj2.getString("title");
        String homepage = jsonObj2.getString("homepage");
        String overview = jsonObj2.getString("overview");
        String release_date = jsonObj2.getString("release_date");
        int runtime = Integer.parseInt(String.valueOf(jsonObj2.getInt("runtime")));
        int revenue = Integer.parseInt(String.valueOf(jsonObj2.getInt("revenue")));

        System.out.println("Title: " + title);
        System.out.println("Homepage: " + homepage);
        System.out.println("Overview: " + overview);
        System.out.println("Released on: " + release_date);
        System.out.println("Runtime: " + runtime + " minutes");
        System.out.println("Revenue: $" + revenue);
        System.out.println("Genres: ");
        for (int i = 0; i < jsonObj2.getJSONArray("genres").length(); i++) {
            String tempString = jsonObj2.getJSONArray("genres").get(i).toString();
            System.out.println(tempString.substring(9, tempString.substring(9).indexOf(",") + 8));
        }
    }
}