import java.util.*;

public class LocationMap {
	public int heatMap[][];

	public LocationMap() {
		heatMap = new int[100][100];
		for(int[] row: heatMap)
			Arrays.fill(row, 0);
	}

	public void addDataValue(DataLine theValue) {
		heatMap[theValue.x][theValue.y]++;
		
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
	/*public static void main(String[] args) {
		LocationMap map = new LocationMap();

		map.printHeatMap();
	}*/


}
