package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import network.NetworkTopology;

public class DataReader {
	
	public static NetworkTopology readNetworkTopologyFromFile(String fileName) {
		try {
			BufferedReader bf =  new BufferedReader(new FileReader(fileName));
			String l = bf.readLine();
			List<Integer> domainNodeCounts = Arrays.asList(l.split(" ")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			for (int i = 1; i<domainNodeCounts.size(); i++) {
				for(int j = 0; j < domainNodeCounts.get(i); j ++) {
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
