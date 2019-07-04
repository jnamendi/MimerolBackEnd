package bmbsoft.orderfoodonline.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import bmbsoft.orderfoodonline.model.FacebookViewModel;

public class FBGraphService {
	private String accessToken;

	public FBGraphService(String accessToken) {
		this.accessToken = accessToken;
	}

	public FacebookViewModel getResFb() {
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		User user = facebookClient.fetchObject("me", User.class);
		if (user == null) {
			return null;
		}
		FacebookViewModel f = new FacebookViewModel();
		f.setId(user.getId());
		f.setName(user.getName());
		f.setLink(user.getLink());

		return f;
	}

	public FacebookViewModel getFBGraph() {
		FacebookViewModel f = new FacebookViewModel();
		JSONParser parser = new JSONParser();
		try {

			String g = "https://graph.facebook.com/app/?access_token=" + accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				JSONObject a = (JSONObject) parser.parse(inputLine);
				String id = (String) a.get("id");
				f.setId(id);

				String link = (String) a.get("link");
				f.setLink(link);

				String name = (String) a.get("name");
				f.setName(name);

				// Loop through each item
				// for (Object o : a) {
				// JSONObject tutorials = (JSONObject) o;
				//
				// String id = (String) tutorials.get("ID");
				// f.setId(id);
				//
				// String link = (String) tutorials.get("link");
				// f.setLink(link);
				//
				// String name = (String) tutorials.get("name");
				// f.setLink(name);
				// }

			}
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return f;
	}

}
