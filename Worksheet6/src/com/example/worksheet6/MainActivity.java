package com.example.worksheet6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	EditText stock;
	TextView stockDisplay;
	Button goButton;
	Thread loadPage;
	String stockPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Set up display*/
		stock = (EditText) findViewById(R.id.stock);
		stockDisplay = (TextView) findViewById(R.id.stockDisplay);
		goButton = (Button) findViewById(R.id.goButton);
		
		
		goButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*Create user's stock object*/
				Stock userStock = new Stock();
				
				/*Load stock string from url*/
				loadPage.start();
				
				/*Wait for thread to finish*/
				try {
					loadPage.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				/*Get company and price from stock string and put in stock object*/
				String[] st = stockPage.split("\"");
				userStock.setCompany(st[25]);
				userStock.setPrice(Double.parseDouble(st[29]));
				
				
				/*Create a JSON object of the stock*/
				Json object = new Json(userStock);
				try {
					object.toJSON();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*Display the JSON Object information in the display*/
				stockDisplay.setText("Company Name:  " + object.getCompany() + "\n" + "Price:  " + object.getPrice());

				
				
			}
		});
		
		
		/*Thread for network*/
		loadPage = new Thread(){
			@Override
			public void run(){
				String URL = "http://finance.yahoo.com/webservice/v1/symbols/" 
						+ stock.getText().toString() + "/quote?format=json";
				StringBuilder inputBuilder = new StringBuilder();
				String input;
				
				URL userInput;
				
					try {
						userInput = new URL(URL);
					
				
				BufferedReader in;
				
					in = new BufferedReader(
							new InputStreamReader(userInput.openStream()));
			
				
				while((input = in.readLine()) != null){
					inputBuilder.append(input);
				}
					}
				
				catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			stockPage = inputBuilder.toString();
			
			}
		};
		
		
	}
}










