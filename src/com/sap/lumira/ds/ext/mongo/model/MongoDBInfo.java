package com.sap.lumira.ds.ext.mongo.model;

public class MongoDBInfo {
	
	private  String SERVERNAME="";//"localhost";
	private  int PORT=27017;//Default portnumber
	private  String USERNAME="";
	private  String PASSWORD="";
	private  String DBNAME="test";//Default Test databse in mongo	
	/**
	 *  This param can be optional as you may be quering from multiple collections.
	 *  
	 */
	private  String COLLECTIONNAME="";//Give collection name here "Products";
	
	public MongoDBInfo(String serverName,
						int port,
						String userName,
						String password,
						String dbName,
						String collectionName) {
		
		SERVERNAME=serverName;
		PORT=port;
		USERNAME=userName;
		PASSWORD=password;
		DBNAME=dbName;
		COLLECTIONNAME=collectionName;
	}

	public void setSERVERNAME(String sERVERNAME) {
		SERVERNAME = sERVERNAME;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public void setDBNAME(String dBNAME) {
		DBNAME = dBNAME;
	}

	public void setCOLLECTIONNAME(String cOLLECTIONNAME) {
		COLLECTIONNAME = cOLLECTIONNAME;
	}

	public MongoDBInfo() {
		 
	}

	public String getSERVERNAME() {
		return SERVERNAME;
	}

	public int getPORT() {
		return PORT;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public String getDBNAME() {
		return DBNAME;
	}

	public String getCOLLECTIONNAME() {
		return COLLECTIONNAME;
	}

}
