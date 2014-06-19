package com.sap.lumira.ds.ext.mongo.facade;

 import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.sap.lumira.ds.ext.mongo.model.MongoDBInfo;
import com.sap.lumira.ds.ext.mongo.query.QueryAndStreamToLumira;

public class SimpleQueryFacade {
	
	private  MongoDBInfo m_serverDetail;

	public SimpleQueryFacade(MongoDBInfo serverDetail) {
		m_serverDetail = serverDetail;
	}
	
public void StreamDataToLumira() {
		
		MongoClient mongoClient;
		
		try {
			
			mongoClient = new MongoClient(m_serverDetail.getSERVERNAME(),
					m_serverDetail.getPORT());

			DB db = mongoClient.getDB(m_serverDetail.getDBNAME());

			/*
			 * Most users run mongodb in a trusted environment , so may not
			 * require the auth boolean auth = db.authenticate(myUserName,
			 * myPassword);
			 */

			QueryAndStreamToLumira queryDB = new QueryAndStreamToLumira(db,
					m_serverDetail.getCOLLECTIONNAME());

			queryDB.mapReduce();
			 
			
			// If you want to query mongo to query a collection see.
			// queryDB.Query method / refer to mongodb java driver documentation further

		} catch (UnknownHostException e) {
			
			//Sends the error information to lumira
			 System.err.print(e.toString());
			 
		}
	}
}
