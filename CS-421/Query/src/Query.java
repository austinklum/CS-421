import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.UnknownFormatConversionException;

public class Query {
	long startDateTime = 1414713600; // 2014-10-31 00:00
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Need to specify datapath argument");
			System.exit(1);
		}
		Query query = new Query(args[0]);
		query.runTool();
	}
	/**
	 * Prompts user with Query
	 */
	private void runTool() {
		Scanner scan = new Scanner(System.in);
		String query = "";
		while(true) {
			System.out.print("QUERY ");
			query = scan.nextLine();
			
			if (query.equals("EXIT")) {
				break;
			}
			
			String[] queryParts = query.split("\\s+");
			
			if(queryParts.length > 6) {
				throw new UnknownFormatConversionException("Expected 6 query parts. Only found " + queryParts.length + " query parts.");
			}
			
			processQuery(queryParts);

		}
		scan.close();
	}
	/**
	 * Parses the queryParts into the needed components to get results.
	 * @param queryParts query string entered by user split on whitespace
	 */
	private void processQuery(String[] queryParts) {
		// This could be another class if need be. I figured we'll be throwing this information away as soon as we query it. 
		// So no point in creating the object just to be queried on and waste memory and speed.
		String ipAddr = queryParts[0];
		int cpu = Integer.parseInt(queryParts[1]);
		long fromDate = dateToEpoch(queryParts[2] + " " + queryParts[3]);
		long toDate = dateToEpoch(queryParts[4] + " " + queryParts[5]);
		
		//More Validation could be done before getting the results...
		System.out.println(getQueryResults(ipAddr, cpu, fromDate, toDate));
	}
	
	/**
	 * Takes the query inputs and searches HashMap to return string of logs from the query.
	 * @param ipAddr IP Address for first HashMap
	 * @param cpu Which CPU to look at 0 or 1
	 * @param fromDate Start of time range
	 * @param toDate End of time range
	 * @return String of logs resulting from query
	 */
	private String getQueryResults(String ipAddr, int cpu, long fromDate, long toDate) {
		int fromDateSinceStart = (int)(fromDate - startDateTime) / 60;
		int dateRangeInMinutes = (int)(toDate - fromDate) / 60;
		
		StringBuilder results = new StringBuilder("CPU" + cpu + " usage on " + ipAddr + ":\n");
		
		// Search through HashMaps and append to results
		for (int i = 0; i < dateRangeInMinutes - 2; i++) {
			results.append(map.get(ipAddr).get(fromDateSinceStart + i).toString(cpu) + ", ");
		}
		results.append(map.get(ipAddr).get(dateRangeInMinutes-1).toString(cpu));
		
		return results.toString();
	}
	
//	private void validate(String ipAddr, int cpu, long fromDate, long toDate) {
//		validateDates(fromDate,toDate);
//		validateIpAddr(ipAddr);
//		validateCpu(cpu);
//	}
	
	/**
	 * Converts date from format 'yyyy-MM-dd HH:mm' to epoch time
	 * @param date format 'yyyy-MM-dd HH:mm'
	 * @return long epoch time
	 */
	private long dateToEpoch(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return format.parse(date).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// IpAddr -> Minute -> ServerLog
	HashMap<String, HashMap<Integer, ServerLog>> map;
	
	
	/**
	 * Parses the log file into a HashMap with IpAddr as key onto a HashMap with Minutes as key onto a ServerLog.
	 * @param filename Where the log information lives
	 */
	public Query(String filename) {
		map = new HashMap<>();
		BufferedReader reader = null;
		String currentLine = "";
		String firstLine = "";
		try {
			reader = new BufferedReader(new FileReader(filename));
			firstLine = reader.readLine();
			currentLine = reader.readLine();
			
			// Read until EOF, populating our HashMaps by IP Address to Minutes to ServerLog objects.
			while(currentLine != null) {
				ServerLog log = new ServerLog(firstLine,currentLine);
				
				if(!map.containsKey(log.getIpAddress())) {
					map.put(log.ipAddress, new HashMap<>());
				}
				
				map.get(log.getIpAddress()).put(log.minutesSinceStartTime(), log);
				
				firstLine = reader.readLine();
				currentLine = reader.readLine();
			}
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private class ServerLog {
		String ipAddress;
		long timeStamp;
		String[] cpuUsage;
		
		/**
		 * Creates a new ServerLog. Each server has two cpu's and so all the data is the same for both lines expect the CPU ID and usage. 
		 * This condenses the two lines into a singular object.
		 * @param logLine1 cpu0 line
		 * @param logLine2 cpu1 line
		 */
		public ServerLog(String logLine1, String logLine2) {
			
			//Split on whitespace
			String[] logParts = logLine1.split("\\s+");
			timeStamp = Long.valueOf(logParts[0]);
			ipAddress = logParts[1];
			
			// The third result is the cpu usage
			// Store into 0 for cpu0 and 1 for cpu1
			cpuUsage = new String[2];
			cpuUsage[0] = logParts[3];
			cpuUsage[1] = logLine2.split("\\s+")[3];
		}
		
		public String toString(int cpu) {
			return "(" + formattedDate() + ", " + cpuUsage[cpu] + "%)";
		}
		
		public String getIpAddress() {
			return ipAddress;
		}
		
		
		/**
		 * Takes the timeStamp epoch and converts into a date string with format 'yyyy-MM-dd HH:mm'
		 * @return timeStamp string with format 'yyyy-MM-dd HH:mm'
		 */
		public String formattedDate() {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return format.format(timeStamp*1000);
		}
		
		public int minutesSinceStartTime() {
			return (int)(timeStamp - startDateTime) / 60;
		}
		
	}
}
