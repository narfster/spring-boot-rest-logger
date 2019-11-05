package com.narfster;

import org.bson.RawBsonDocument;

public class Bson {
	public static String Deserialization(byte[] arr){

		RawBsonDocument rawdo = new RawBsonDocument(arr);
		return rawdo.toJson();
	}
}
