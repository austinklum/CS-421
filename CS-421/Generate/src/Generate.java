import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generate {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Need to specify datapath argument");
			System.exit(1);
		}
		Generate gen = new Generate();
		try {
			gen.generate(args[0]);
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	private void generate(String filename) throws IOException {
	    long today = 1414713600; // 2014-10-31 00:00
	    int minutesInDay = 1440;
	    int numberOfServers = 250;
	    int hostMax = 3; //max 3.254 // min 0.1
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    long tot = 0;
	    //Total lines written per day 2,880,0000.
	    // 1000 Serves * 2 CPU's * 1440 minutes in a day
	    for (int time = 0; time < minutesInDay; time++ ) {
	    	long timeStamp = today+time*60;
		    for(int i = 0; i <= hostMax; i++) {
		    	String ipAddress = "192.168." + i + ".";
		    	for(int server = 1; server <= numberOfServers; server++) {
		    		tot += 2;
		    		writer.write(timeStamp + " " + ipAddress + server + " 0 "+ (new Random().nextInt(100) + 1) + "\n");
		    		writer.write(timeStamp + " " + ipAddress + server + " 1 "+ (new Random().nextInt(100) + 1) + "\n");
		    		System.out.println(tot);
		    	}
		    	writer.flush();
		    }
	    }
	    writer.close();
	}
}
