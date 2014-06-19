package com.sap.lumira.ds.ext.datastream;

import com.sap.lumira.ds.ext.mongo.model.MongoDBInfo;

/**
 *  Stream helper class demostrates streaming data to lumira.
 *  Note: You can seperate data streaming to static helper functions
 *  which can do streaming . SteamDSInfo method demostrates the same.
 * @author Devicharan vinnakota
 *
 */
public class StreamHelper {
	
	/**
	 * Stream dataset information to lumira.
	 * Background : Lumira expects the dataset info to be given to it. Since the
	 * data is transfered in csv mode the below format would be necessary.
	 * @param dsInfo
	 */
	public static void StreamDSInfo(MongoDBInfo dsInfo){
		
		System.out.println("beginDSInfo");

		System.out.println("csv_separator;,;true");
		System.out.println("csv_date_format;M/d/yyyy;true");
		System.out.println("csv_number_grouping;,;true");
		System.out.println("csv_number_decimal;.;true");
		System.out
				.println("csv_first_row_has_column_names;true;true");
		 
		System.out
				.println("lumongo_host;" + dsInfo.getSERVERNAME() + ";true");
		System.out.println("lumongo_port;" + dsInfo.getPORT() + ";true");
		System.out
				.println("lumongo_user;" + dsInfo.getUSERNAME() + ";true");
		System.out
				.println("lumongo_pass;" + dsInfo.getPASSWORD() + ";true");
		System.out.println("lumongo_collection;"
				+ dsInfo.getCOLLECTIONNAME() + ";true");
		System.out
				.println("lumongo_dbname;" + dsInfo.getDBNAME() + ";true");

		System.out.println("endDSInfo");
		
	}

}
