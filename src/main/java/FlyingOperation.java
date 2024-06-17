import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FlyingOperation {

    public Map<String,String> minimumTimeToFly(Map<String, List<Ticket>> mapTicket) {
        Map<String,String> map = new HashMap<>();

        for (var entry : mapTicket.entrySet()) {
            var val = entry.getValue();
            map.put(entry.getKey(),findMin(val));
        }
        return map;
    }

    private String findMin(List<Ticket> list) {
        String departureTime = list.get(0).getDepartureTime();
        String arrivalTime = list.get(0).getArrivalTime();


        var currentMin = getDeltaBetweenTwoTime(departureTime,arrivalTime);
        for( int i = 1; i < list.size(); i++){
            String depTime = list.get(i).getDepartureTime();
            String arrTime = list.get(i).getArrivalTime();


            var secondMin = getDeltaBetweenTwoTime(depTime,arrTime);
            if(currentMin.isAfter(secondMin)){
                currentMin = secondMin;
            }
        }

        return String.valueOf(currentMin);
    }

    private LocalTime getDeltaBetweenTwoTime(String departureTime, String arrivalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime startTime = LocalTime.parse(departureTime, formatter);
        LocalTime endTime = LocalTime.parse(arrivalTime, formatter);


        Duration duration = Duration.between(startTime, endTime);

        int hours = (int) duration.toHours();
        int minutes = (int) (duration.toMinutes() % 60);

        return LocalTime.of(hours, minutes);
    }

    public Map<String, List<Ticket>> collectCarrierToTicketMap(List<Ticket> list) {
        Map<String, List<Ticket>> map = new HashMap<>();

        for (var ticket : list) {
            if(!map.containsKey(ticket.getCarrier())){
                List<Ticket> t = new ArrayList<>();
                t.add(ticket);
                map.put(ticket.getCarrier(),t);
            }else {
                var t =  map.get(ticket.getCarrier());
                t.add(ticket);
                map.put(ticket.getCarrier(),t);
            }
        }
        return map;
    }
}

