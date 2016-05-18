import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class LocationMap {
	public int heatMap[][];

	public LocationMap() {
		heatMap = new int[100][100];
		for(int[] row: heatMap)
			Arrays.fill(row, 0);
	}

	public void addDataValues(ArrayList<DataLine> theValues) {
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
    	 System.out.println("Max was in: "+ positionX+ " " + positionY);
		 System.out.println(maxValue);
	}

	public void saveCSV(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);

			writer.append("PositionX");
			writer.append(",");
			writer.append("PositionY");
			writer.append(",");
			writer.append("Amount");
			writer.append("\n");

			for (int i = 0; i < heatMap.length; i++) {
    			for (int j = 0; j < heatMap.length; j++) {
    				if(heatMap[i][j] != 0) {
	    				writer.append(new Integer(i).toString());
	    				writer.append(",");
	    				writer.append(new Integer(j).toString());
	    				writer.append(",");
	    				writer.append(new Integer(heatMap[i][j]).toString());
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

}
