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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	String URL, stockName;
	StringBuilder inputBuilder;
	
	
	
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

				URL = "http://finance.yahoo.com/webservice/v1/symbols/" 
						+ stock.getText().toString() + "/quote?format=json";
				
				/*Initial run*/
				new DownloadStock().execute(URL);
				stockName = stock.getText().toString();
				
			/*Thread and handler for updates*/	
			final Handler threadHandler = new Handler();
				
				Thread loadStock = new Thread(){
					public void run(){
						String localStock = stockName;
						while(localStock == stockName){
						
						try {
							Thread.sleep(10000);
							threadHandler.post(new Runnable(){
								@Override 
								public void run(){
									new DownloadStock().execute(URL);
								}
							});
							
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				};
				
				loadStock.start();

				
			}
				
			});

	
	}

	
	
	
	private class DownloadStock extends AsyncTask<String, String, String>{
	

		@Override
		protected String doInBackground(String... params) {
				URL userInput;
				String stockPage, input;
				inputBuilder = new StringBuilder();
				
					try {
						userInput = new URL(params[0]);
					
				
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
			return stockPage;
		}
		protected void onPostExecute(String result){
			
			/*Get company and price from stock string and put in stock object*/
			Stock userStock = new Stock();
			String[] st = result.toString().split("\"");
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

	}

	
	
		
	}
	
	
			










