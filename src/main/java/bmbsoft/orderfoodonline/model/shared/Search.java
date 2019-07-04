package bmbsoft.orderfoodonline.model.shared;

import org.json.simple.JSONObject;

public class Search {
	private JSONObject predicateObject;

	public JSONObject getPredicateObject() {
		return predicateObject;
	}

	public void setPredicateObject(JSONObject predicateObject) {
		this.predicateObject = predicateObject;
	}
}
