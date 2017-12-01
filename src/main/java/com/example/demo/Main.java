package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Main {

	public static void main(String[] argv) {
        
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateInString = "2001-05-01 12:34:56.789";

        try {
            Date date = formatter.parse(dateInString);
            Date date1 = convertDateIngivenTimezoneReport(date, null, -120);
            System.out.println(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
	
	public static Date convertDateIngivenTimezoneReport(Date dateInServerTz, String timezone, int browserTzOffset) {
		DateTime dateToConvertFromTimeZone = new DateTime(dateInServerTz);
		DateTime dateConvertedGivenTimeZone = null;
		TimeZone preferredTimeZone = TimeZone.getDefault();
		
		if (timezone != null){
			preferredTimeZone = TimeZone.getTimeZone(timezone);
			dateConvertedGivenTimeZone = dateToConvertFromTimeZone.withZone(DateTimeZone.forTimeZone(preferredTimeZone));
		}else{
			DateTime dstDateTime = dateToConvertFromTimeZone.withZone(DateTimeZone.forOffsetMillis(browserTzOffset*60*1000));
			preferredTimeZone = TimeZone.getTimeZone(dstDateTime.getZone().toTimeZone().getID());
			System.out.println(preferredTimeZone.getDSTSavings());
			dateConvertedGivenTimeZone = dateToConvertFromTimeZone.withZone(DateTimeZone.forTimeZone(preferredTimeZone));			
		}
		return dateConvertedGivenTimeZone.toLocalDateTime().toDate();
	}
}
