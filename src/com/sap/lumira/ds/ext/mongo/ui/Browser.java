package com.sap.lumira.ds.ext.mongo.ui;

import netscape.javascript.JSObject;

import org.w3c.dom.Document;

import com.sap.lumira.ds.ext.datastream.StreamHelper;
import com.sap.lumira.ds.ext.mongo.facade.SimpleQueryFacade;
import com.sap.lumira.ds.ext.mongo.model.MongoDBInfo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * JavaFx Browser Scene to Display Webview.
 * @author Devicharan vinnakota
 *
 */
public class Browser extends Region {

	final WebView browser = new WebView();

	final WebEngine webEngine = browser.getEngine();
	
	 
	
	String userName;
	String password;
	String hostName;
	String port;
	String dbName;
	String collectionName;

	 
	int size;
	String params;

	public Browser(String[]args) {
		 
 
		/**
		 * Get the Html page bundled in the webcontent folder.
		 */
		//String urlobj =Browser.class.getResource("../../../../../../../testcustomjs.html").toExternalForm();
		String urlobj=this.getClass().getClassLoader().getResource("testcustomjs.html").toExternalForm();
		 
		webEngine.setJavaScriptEnabled(true);

		webEngine.documentProperty().addListener(
				new ChangeListener<Document>() {
					@Override
					public void changed(
							ObservableValue<? extends Document> prop,
							Document oldDoc, Document newDoc) {
						// enableFirebug(webEngine);
					}
				});
		webEngine.getLoadWorker().exceptionProperty()
				.addListener(new ChangeListener<Throwable>() {
					@Override
					public void changed(
							ObservableValue<? extends Throwable> ov,
							Throwable t, Throwable t1) {
						System.out.println("Received exception: "
								+ t1.getMessage());
					}
				});

		webEngine.getLoadWorker().stateProperty()
				.addListener(new ChangeListener<Worker.State>() {
					@Override
					public void changed(ObservableValue<? extends State> ov,
							State t, State t1) {
						if (t1 == Worker.State.SUCCEEDED) {
							JSObject window = (JSObject) webEngine
									.executeScript("window");
							window.setMember("app", new App());
						}
					}

				 
				});

		// Load the SAPUI5  url here.
		webEngine.load(urlobj);

		// add the web view to the scene
		getChildren().add(browser);

	}

	 

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}
	
	 
	
	void readDataFromMongo() {
		
		//Data will be taken from memeber variables.

		MongoDBInfo serverDetails = new MongoDBInfo(hostName,
				Integer.parseInt(port), userName, password, dbName,
				collectionName);
		
		//User simple queryface

		SimpleQueryFacade qfs=new SimpleQueryFacade(serverDetails);
		qfs.StreamDataToLumira();
	}
	
	
/**
 * Helper class to bridge communication between Webview & JavaFx
 * @author Divya parashar
 *
 */
	public class App {
		/**
		 * This method will be called from JavaScript on clicking FetchData
		 * button in html view.
		 * @param userName
		 * @param Port
		 * @param DBName
		 * @param collectionName
		 * @param hostName
		 */
		public void setInputParam(String userName, String Port, String DBName,
				String collectionName, String hostName) {
			/**
			 *  1) Get Data from SAPUI5
			 *  2) Query MongoDB
			 *  3) Stream to Lumira
			 */
			
			MongoDBInfo infoObj=new MongoDBInfo(hostName,
									Integer.parseInt(Port),
									userName,
									" ",
									DBName, 
									collectionName);
			
			StreamHelper.StreamDSInfo(infoObj);			
			SimpleQueryFacade qfs=new SimpleQueryFacade(infoObj);
			qfs.StreamDataToLumira();
			/*
			 *  Exit the app.
			 */
			kill();
			 

		}

		public void kill() {
			Platform.exit();
			 
		}
	}
}

