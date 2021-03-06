package com.opendrive.android.parser;

import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class SharedFolderDataParser {

	private boolean mHasShareFolders = false;

	public SharedFolderDataParser() {
		// TODO Auto-generated constructor stub
	}

	public boolean parseResponse(String responseXml) throws IOException {

		XmlPullParser parser = Xml.newPullParser();

		try {
			// auto-detect the encoding from the stream
			parser.setInput(new StringReader(responseXml));

			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				String name = null;

				switch (eventType) {

				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase("HasShareFolders")) {
						mHasShareFolders = Boolean.parseBoolean(parser.nextText());
					}
					break;

				case XmlPullParser.END_TAG:
					break;
				}

				eventType = parser.next();
			}
			
			return mHasShareFolders;
		}
		catch (XmlPullParserException e) {
			throw new IOException(e.toString());
		}
	}
}
