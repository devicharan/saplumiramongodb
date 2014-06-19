package com.sap.lumira.ds.ext.mongo.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

/**
 * Actual MongoDb Querying using mongo java driver is performed.<br>
 * The dataset info should be streamed in below format.<br>
 * <code>
 *  beginDSInfo 
	csv_separator;,;true <br>
	csv_date_format;M/d/yyyy;true <br>
	csv_number_grouping;,;true<br>
	csv_number_decimal;.;true
	csv_first_row_has_column_names;true;true
	lumongo_host;localhost;true
	lumongo_port;27017;true
	lumongo_user;abc;true
	lumongo_pass;[C@14b746c8;true
	lumongo_collection;testcollection;true
	lumongo_dbname;test;true
	endDSInfo
 * 
 * </code>
 * @author Devicharan Vinnakota
 * 
 */
public class QueryAndStreamToLumira {

	BasicDBObject m_obj;
	DB m_db;
	String m_Collection;

	public QueryAndStreamToLumira(DB db, String collectionName) {

		m_db = db;
		m_Collection = collectionName;

	}


	// Below is sample code that was left out commented . It demonstrates how data can be retrieved using a collection directly and stream it to lumira
	/**
	 * Lumira Needs data to be in tabular format .So loop through the data
	 * and push into the stdout .
	 * @param query
	 * @param isAggregate
	 * @param fields
	 * @param header
	 *//*
	
	public void Query(List<DBObject> query, Boolean isAggregate,
			BasicDBObject fields, StringBuilder header) {
		*//**
		 *  This method is not executed but left here as an example
		 *  to query mongodb collection and stream to lumira
		 *//*

		DBCursor cursor = null;

		AggregationOutput output;

		System.out.println("beginData");// Lumira needs this tag to recognize
										// data start..
		System.out.println(header.toString());

		if (!isAggregate) {

			cursor = m_db.getCollection(m_Collection)
					.find(query.get(0), fields);

			try {
				while (cursor.hasNext()) {

					try {
						DBObject tmp = cursor.next();

						Map map = new LinkedHashMap(tmp.toMap());
						Iterator it = map.entrySet().iterator();

						StringBuilder wrt = new StringBuilder();
						Boolean firstElement = true;

						while (it.hasNext()) {
							Map.Entry mapEntry = (Map.Entry) it.next();

							if (firstElement) {

								wrt.append(mapEntry.getValue());
								firstElement = false;
							} else {

								wrt.append("," + mapEntry.getValue());

							}

						}

						System.out.println(wrt.toString());
					} catch (Exception e) {

						throw e;
					}

				}

			} finally {
				cursor.close();
			}
		} else {

			output = m_db.getCollection(m_Collection).aggregate(query);

			for (final DBObject result : output.results()) {

				System.out.println(result);
			}
		}
		System.out.println("endData");

	}
 */
	/**
	 * This method shows how mapreduce functionality of mongodb be used and stream
	 * data inline to lumira.
	 * Note: Howerver you can pass result object to a static helper method which writes
	 * it out to stdout using system.out...
	 */
	public void mapReduce() {
		
		System.out.println("beginData");
		
		try {
			 

			// access the input collection
			DBCollection collection = m_db.getCollection(m_Collection);
			
			// read Map file
			String map = readFile("wc_map.js");
			
			// read Reduce file
			String reduce = readFile("wc_reduce.js");
			
			 
			//Prepare mapreduce command to be fired on the collection giving the map and reduce javascript files as input 
			MapReduceCommand cmd = new MapReduceCommand(collection, map,
					reduce, null, MapReduceCommand.OutputType.INLINE, null);

			//fire the mapreduce command
			MapReduceOutput out = collection.mapReduce(cmd);
			
			//The below is csv header streamed to lumira
			System.out.println("word,count");
			
			//Loop through the result set and start streaming to lumira inline
			for (DBObject o : out.results()) {
				DBObject idOBj = (BasicDBObject) o.get("_id");
				String word = (String) idOBj.get("word");

				DBObject obj = (BasicDBObject) o.get("value");
				Double count = (Double) obj.get("count");
				 

				System.out.println(word + ","
						+ Integer.toString(count.intValue()));
				 
			}
		} catch (Exception e) {
			//Sends the error information to lumira
			 System.err.print(e.toString());
		}
		//Notify lumira that data streaming has ended
		System.out.println("endData");
	}

	/**
	 * Reads the specified file from classpath.
	 * This function is used to load mapreduce files which were part of
	 * package.
	 */
	private static String readFile(String fileName) throws IOException {
		// get the input stream
		InputStream fileStream = QueryAndStreamToLumira.class.getResourceAsStream(fileName);
		// create a buffer with some default size
		byte[] buffer = new byte[8192];
		// read the stream into the buffer
		int size = fileStream.read(buffer);
		// create a string for the needed size and return
		return new String(buffer, 0, size);
	}
}
