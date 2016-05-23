import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.text.DateFormat;



public class LocationMap {
	public int heatMap[][];
	public int heatMapwithTimes[][];
	public Map<Integer, Coordinate> sortedMap;
	public String CSVFile;

	public LocationMap(String fileLocation) {
		CSVFile = fileLocation; 
		heatMap = new int[100][100];
		heatMapwithTimes = new int[100][100];

		for(int[] row: heatMap)
			Arrays.fill(row, 0);
		for(int[] row: heatMapwithTimes)
			Arrays.fill(row, 0);
		
	}

	//Creates a global heatMap so it can be used by the Visitor Class-
	//Also creates a local heatMap and saves it to the CSV. The local heatMap is
	//zeroed for each increment of hour.
	public void addDataValues(ArrayList<DataLine> theValues) {
		createCSV(CSVFile);
		for(int i = 8; i < 24; i++) {
			for(DataLine data : theValues) {
				if(data.timestamp.getHours() == i)
					heatMapwithTimes[data.x][data.y]++;
			}
			saveCSV(CSVFile, i);
			for(int[] row: heatMapwithTimes)
				Arrays.fill(row, 0);

		}

		for(DataLine data : theValues) {
				heatMap[data.x][data.y]++;
		}
	}

	public void printHeatMap() {
		for(int i = 1; i < 99; i++) {
			for(int j = 1; j < 99; j++) { 
				System.out.print(heatMap[i][j] + " ");
			}
			System.out.println();
		}

	}

	public void printMaxValue() {
		int maxValue = 0;
		int positionX = 0;
		int positionY = 0;
		for (int i = 0; i < heatMap.length; i++) {
    		for (int j = 0; j < heatMap.length; j++)
		        if (heatMap[i][j] > maxValue) {
		            maxValue = heatMap[i][j];
		            positionX = i;
		            positionY = j;
		        }
		       
    	}
    	 System.out.println("Max was in: " + positionX + " " + positionY);
		 System.out.println(maxValue);
	}
	public void createCSV(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);

			writer.append("PositionX");
			writer.append(",");
			writer.append("PositionY");
			writer.append(",");
			writer.append("Amount");
			writer.append(",");
			writer.append("Group");
			writer.append("\n");
			writer.flush();
	    	writer.close();

		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void saveCSV(String fileName, Integer Group) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			for (int i = 0; i < heatMapwithTimes.length; i++) {
    			for (int j = 0; j < heatMapwithTimes.length; j++) {
    				if(heatMapwithTimes[i][j] != 0) {
	    				writer.append(new Integer(i).toString());
	    				writer.append(",");
	    				writer.append(new Integer(j).toString());
	    				writer.append(",");
	    				writer.append(new Integer(heatMapwithTimes[i][j]).toString());
	    				writer.append(",");
	    				writer.append(new Integer(Group).toString());
	    				writer.append("\n");
	    			}
    			}
    		
    		}
    		
    		writer.flush();
	    	writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void sortMap() {
		sortedMap = new TreeMap<Integer, Coordinate>(Collections.reverseOrder());

		for(int i = 0; i < heatMap.length; i++) {
			for(int j = 0; j < heatMap.length; j++) {
				if (heatMap[i][j] != 0)
				{
					sortedMap.put(heatMap[i][j], new Coordinate(i, j));
				}
			}
		}
	}

}
