package com.jasonsparc.pletoon.sqlbrite;

/**
 * Created by jasonsparc on 4/22/2016.
 */
public class BriteUtils {

	// TODO Rename the following to something better.

	public static String getDropIfExists(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName;
	}
}
