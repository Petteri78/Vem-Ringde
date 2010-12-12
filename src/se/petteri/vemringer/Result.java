package se.petteri.vemringer;

import org.json.JSONArray;
import org.json.JSONException;

public class Result {
	
	private String name;
	private String address;
	private String city;
	
	public Result(JSONArray jsonArray) throws JSONException {
		this.name = jsonArray.getString(0);
		this.address = jsonArray.getString(1);
		this.city = jsonArray.getString(2);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
}
