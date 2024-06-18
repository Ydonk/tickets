import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static FlyingOperation flyingOperation = new FlyingOperation();
    private static PriceCalculation priceCalculation = new  PriceCalculation();




    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("fail");
        }

        String filePath = args[0];
        String from = args[1];
        String to = args[2];

        ObjectMapper mapper = new ObjectMapper();

        try {
            TicketWrapper tickets = mapper.readValue(new File(filePath), new TypeReference<>() {
            });

            List<Ticket> relevantTickets = tickets.getTicketList().stream()
                    .filter(t -> from.equals(t.getOriginName()) && to.equals(t.getDestinationName()))
                    .collect(Collectors.toList());

            if(relevantTickets.isEmpty()) {
                throw new IllegalArgumentException("Не смог найти ни одного рейса с " + from + " до " + to + "\n"
                        + "проверьте корректность введенных данных");
            }

            var currentMap =  flyingOperation.collectCarrierToTicketMap(relevantTickets);
            var resultMap =  flyingOperation.minimumTimeToFly(currentMap);
            priceCalculation.setStringListMap(tickets.getTicketList());
            var diffAvgAndMedian =  priceCalculation.diffAvgAndMedian();


            for (var entry : resultMap.entrySet()){
                System.out.println("For the airline " + entry.getKey() + " Minimum flight time " + entry.getValue() +
                        " Diff between avg and median = " + diffAvgAndMedian);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


