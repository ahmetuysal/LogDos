package util;

import exception.NoSuchRoutableTypeException;
import network.*;
import network.logging.strategy.LoggingStrategyType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    public static AutonomousSystemTopology readAutonomousSystemTopologyFromFile(String topologyFileName, String transientAutonomousSystemsFileName , LoggingStrategyType loggingStrategyType, double falsePositiveRate) {
        AutonomousSystemTopology autonomousSystemTopology = new AutonomousSystemTopology();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(topologyFileName));
            String line = bf.readLine();
            while (line != null) {
                String[] ASIds = line.split(" ");
                int ASId1 = Integer.parseInt(ASIds[0]);
                int ASId2 = Integer.parseInt(ASIds[1]);

                if (!autonomousSystemTopology.hasAutonomousSystemById(ASId1)) {
                    AutonomousSystem as1 = new AutonomousSystem(ASId1, AutonomousSystemType.CORE, loggingStrategyType, falsePositiveRate);
                    autonomousSystemTopology.addAutonomousSystem(as1);
                }

                if (!autonomousSystemTopology.hasAutonomousSystemById(ASId2)) {
                    AutonomousSystem as2 = new AutonomousSystem(ASId2, AutonomousSystemType.CORE, loggingStrategyType, falsePositiveRate);
                    autonomousSystemTopology.addAutonomousSystem(as2);
                }

                AutonomousSystem as1 = autonomousSystemTopology.getAutonomousSystemById(ASId1);
                AutonomousSystem as2 = autonomousSystemTopology.getAutonomousSystemById(ASId2);
                Route route = new Route(as1, as2);

                as1.addRoute(route);
                as2.addRoute(route);
                autonomousSystemTopology.addRoute(route);
                line = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bf = new BufferedReader(new FileReader(transientAutonomousSystemsFileName));
            String line = bf.readLine();
            while (line != null) {
                int asId = Integer.parseInt(line.trim());
                AutonomousSystem transientAS = autonomousSystemTopology.getAutonomousSystemById(asId);
                if (transientAS == null) {
                    transientAS = new AutonomousSystem(asId, AutonomousSystemType.TRANSIENT, loggingStrategyType, falsePositiveRate);
                    autonomousSystemTopology.addAutonomousSystem(transientAS);
                } else {
                    transientAS.setType(AutonomousSystemType.TRANSIENT);
                }
                line = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return autonomousSystemTopology;
    }

}
