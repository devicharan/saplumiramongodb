package com.sap.lumira.ds.ext.mongo.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.sap.lumira.ds.ext.datastream.DSInfoConstants;
import com.sap.lumira.ds.ext.mongo.facade.SimpleQueryFacade;
import com.sap.lumira.ds.ext.mongo.model.MongoDBInfo;

public class UI5WebView extends Application {

	private Scene scene;

	Stage m_stage = null;

	static String[] m_arguments;
	Mode mode;
	int size;
	String params;

	MongoDBInfo dbInfo = new MongoDBInfo();

	enum Mode {
		PREVIEW, REFRESH, EDIT
	};

	@Override
	public void start(Stage stage) {

		/*
		 * You have to parse arguments here itself inorder to run the connector
		 * in headless mode. Headless mode is required during "Data Refresh"
		 */
		parseArguments(m_arguments);

		if (mode == Mode.REFRESH) {
			/**
			 * You are in refresh mode 1) Parse refresh data passed by lumira 2)
			 * Query MongoDB 3) Stream back data to lumira
			 */
			parseRefreshArguments();
			SimpleQueryFacade qfs = new SimpleQueryFacade(dbInfo);
			qfs.StreamDataToLumira();
			// Close the app. Its not required anymore.
			Platform.exit();

		} else if (mode == Mode.PREVIEW || mode == Mode.EDIT) {

			// You require this proxy settings only if you want to load firebug
			/*
			 * System.setProperty("http.proxyHost", "proxy");
			 * System.setProperty("http.proxyPort", "1234");
			 */

			// create the scene
			m_stage = stage;
			m_stage.setTitle("SAPLumira MongoDB Connector");

			Rectangle2D primScreenBounds = Screen.getPrimary()
					.getVisualBounds();
			Double x = (primScreenBounds.getWidth() * 0.75);
			Double y = (primScreenBounds.getHeight() * 0.90);

			scene = new Scene(new Browser(m_arguments), x, y,
					Color.web("#666970"));
			m_stage.setScene(scene);

			// Remove the Titlebar
			m_stage.initStyle(StageStyle.UNDECORATED);
			m_stage.show();
		}

	}

	/**
	 * Parse arguments passed and sets mode and params.
	 */
	void parseArguments(String[] args) {
		if (args.length > 0) {
			// mode = Mode.ERROR;
			for (int i = 0; i < args.length; i++) {
				if (args[i].equalsIgnoreCase("-mode") && i + 1 < args.length) {
					if (args[i + 1].equalsIgnoreCase("preview")) {
						mode = Mode.PREVIEW;
					} else if (args[i + 1].equalsIgnoreCase("edit")) {
						mode = Mode.EDIT;
					} else if (args[i + 1].equalsIgnoreCase("refresh")) {
						mode = Mode.REFRESH;
					}
				} else if (args[i].equalsIgnoreCase("-size")
						&& i + 1 < args.length) {
					size = Integer.parseInt(args[i + 1]);
				} else if (args[i].equalsIgnoreCase("-params")
						&& i + 1 < args.length) {
					params = args[i + 1];
				}
			}
		}
	}

	/**
	 * Helper method to Parse Arguments in Refresh Mode..
	 */
	void parseRefreshArguments() {

		if (mode == Mode.REFRESH) {
			// Reload Data here....
			String[] lines = params.split(";");

			for (int index = 0; index < lines.length; index++) {
				String[] tokens = lines[index].split("=");

				if (tokens.length > 1) {

					String tokenKey = tokens[0];
					String tokenValue = tokens[1];

					switch (tokenKey) {

					case DSInfoConstants.MONGODBHOST:
						dbInfo.setSERVERNAME(tokenValue);
						break;
					case DSInfoConstants.PORT:
						dbInfo.setPORT(Integer.parseInt(tokenValue));
						break;
					case DSInfoConstants.USER:
						dbInfo.setUSERNAME(tokenValue);
						break;
					case DSInfoConstants.PASS:
						dbInfo.setPASSWORD(tokenValue);
						break;
					case DSInfoConstants.DBNAME:
						dbInfo.setDBNAME(tokenValue);
						break;
					case DSInfoConstants.COLLECTION:
						dbInfo.setCOLLECTIONNAME(tokenValue);
						break;
					default:
						break;

					}
				}
			}
		}
	}

	public static void main(String[] args) {

		UI5WebView.m_arguments = args;
		/*
		 * You can pass custom arguments to test your program here or do it in
		 * your eclipse program arguments. Ex: Below code puts the program in
		 * preview mode. final String[] params = { "-mode", "preview" };
		 * UI5WebView.m_arguments=params;
		 */
		 
		launch(args);
	}

}
