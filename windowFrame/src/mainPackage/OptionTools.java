package mainPackage;
//Optimalere Lösung durch eine Option List und erweitertes for;


import java.io.*;
import java.util.regex.Pattern;

class OptionTools {
	
	static void IOoptionloader() {
		BufferedReader options = null;
		while(true){
			if(new File("./options.txt").isFile()){
			try{
				options = new BufferedReader(new FileReader("Options.txt"));
				MainFrame.WindowWidth = bufferedInput(options);
				MainFrame.WindowHeight = bufferedInput(options);
				options.close();
				break;
				}catch(IOException IOE){
					try {if (options!=null)
						options.close();
					} catch (IOException e) {}
					resetToStandartOptions();
					System.err.println("Fehler beim Einlesen der Optionen");
					//IOE.printStackTrace();
					System.out.println(IOE);
				}
			}
			else resetToStandartOptions();
			
			}
		//System.out.println(MainFrame.WindowWidth+" "+MainFrame.WindowHeight);
	}

	private static int bufferedInput(BufferedReader options) throws NullPointerException, IOException {
		String n;
		n = options.readLine();
		if(n==null||!Pattern.matches("\\d+", n))
		 throw new IOException("Du Depp hast die Options-Datei geändert!");
		else return Integer.parseInt(n);
	}

	private static void resetToStandartOptions() {
		MainFrame.WindowWidth = 800;
		MainFrame.WindowHeight = 600;
		new File("./options.txt").delete();
		
		PrintWriter pw = null;
		try
		{
		  Writer fw = new FileWriter( "options.txt" );
		  Writer bw = new BufferedWriter( fw );
		  pw = new PrintWriter( bw );

		    pw.println(MainFrame.WindowWidth);
		    pw.println(MainFrame.WindowHeight);
		}
		catch ( IOException IOE ) {
		  System.err.println( "Error creating file!" );
		}
		finally {
		  if ( pw != null )
		    pw.close();
		}		
	}	
}
