package com.AWS.TMDB;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
@Controller
public class TmdbApplication {

    private String version = "0.2\n";
    private String description = "In order to use this service type [IP]:8761/[series name] in browser to search.";
    private TmdbController TMDB = new TmdbController();
    private String jsonInString;
    String sql = null;
    String lastUpdate = "No information";
    String nextEpisode = "No information";


    public static void main(String[] args) {
        SpringApplication.run(TmdbApplication.class, args);
    }


    @RequestMapping(value = "/")
    @ResponseBody
    private String emptyURL() {
        return "TMDB Microservice Version: " + version + '\n' + description;
    }

    @RequestMapping("/{searchInput}")
    private @ResponseBody
    String getAttr(@PathVariable(value = "searchInput") String input) {
        // Format String for http query
        String search = input.replaceAll(" ", "+");
        search = search.replaceAll("%", "+");
        int selectedID = Integer.parseInt(search);

        // Do Magic
        DBConnector.executeSQL(getquery(selectedID));
        lastUpdate = "No information";
        nextEpisode = "No information";

        /*
        try {
            lastUpdate = TMDB.getNextEpAr(selectedID).getString("air_date");
        } catch (Exception e3) {
            System.out.println("No last Update");
            System.out.println(e3);
        }
        try {
            nextEpisode = TMDB.downloadResultJSON(selectedID).getString("air_date");
        } catch (Exception e4) {
            System.out.println("No episode");
            System.out.println(e4);
        }

        sql = "INSERT INTO SeriesTicker.TVSeries " + "VALUES (" + selectedID + ", '" + resultComboBox.getValue().toString() + "', 1, 1, '" + lastUpdate + "','" + nextEpisode + "')";
        DBConnector.executeSQL(sql);
        */
        // AB HIER SAME
        /*
        // Download JSON with query
        JSONArray resultJSONArray = TMDB.resultJSONObject(search);
        // Form to string
        jsonInString = resultJSONArray.toString();

		/* TODO Testing this:
		System.out.println(jsonArray);
		System.out.println(jsonArray.get(1));
		*/

        return jsonInString;
    }

    private String getquery(int requestID) {
        JSONObject currentTVSeries = TMDB.downloadResultJSON(requestID);
        String name = currentTVSeries.getString("name");
        try {
        String lastUpdate = currentTVSeries.getJSONObject("last_episode_to_air").getString("air_date");
        } catch (Exception e3) {
            System.out.println("No last Update");
            System.out.println(e3);
        }
        try {
        String nextEpisode = currentTVSeries.getJSONObject("next_episode_to_air").getString("air_date");
        } catch (Exception e4) {
            System.out.println("No episode");
            System.out.println(e4);
        }
        System.out.println(lastUpdate + "URHENSOHN" + nextEpisode);
        String query = "INSERT INTO SeriesTicker.TVSeries " + "VALUES (" + requestID + ", '" + name + "', 1, 1, '" + lastUpdate + "','" + nextEpisode + "')";

        return query;

    }

}

