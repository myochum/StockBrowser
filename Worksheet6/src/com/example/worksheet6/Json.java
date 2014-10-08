package com.example.worksheet6;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {
	private Stock userStock;
	
	public Json(Stock stock){
		this.userStock = stock;
	}
	
	public void toJSON() throws JSONException{
		
		JSONArray stockList = new JSONArray();
		
		JSONObject stockJSon = new JSONObject();
		
		stockJSon.put("company", userStock.getCompany());
		stockJSon.put("price", userStock.getPrice());
		
		stockList.put(stockJSon);
	
	}
	
	public String getCompany(){
		return userStock.getCompany();
	}
	
	public double getPrice(){
		return userStock.getPrice();
	}

}
