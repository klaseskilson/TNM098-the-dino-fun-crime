import java.util.*;

public class LocationMap {
	private int heatMap[][];

	public LocationMap() {
		heatMap = new int[99][99];
		for(int[] row: heatMap)
			Arrays.fill(row, 0);
	}

	public void addDataValue(dataLine theValue) {
		heatMap[theValue.x]++;
		heatMap[theValue.y]++;
	}

	public void printHeatMap() {
		for(int i = 0; i < 99; i++) {
			for(int j = 0; j < 99; j++) { 
				System.out.print(heatMap[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static void main(String[] args) {
		LocationMap map = new LocationMap();

		map.printHeatMap();
	}


}
