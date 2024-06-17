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
        String departureDate = list.get(0).getDepartureDate();
        String arrivalTime = list.get(0).getArrivalTime();
        String arrivalDate = list.get(0).getArrivalDate();

        var currentMin = getDeltaBetweenTwoTime(departureDate,arrivalDate,departureTime,arrivalTime);
        for( int i = 1; i < list.size(); i++){
            String depTime = list.get(i).getDepartureTime();
            String depDate = list.get(i).getDepartureDate();
            String arrTime = list.get(i).getArrivalTime();
            String arrDate = list.get(i).getArrivalDate();

            var secondMin = getDeltaBetweenTwoTime(depDate,arrDate,depTime,arrTime);
            if(currentMin.isAfter(secondMin)){
                currentMin = secondMin;
            }
        }

        return String.valueOf(currentMin);
    }

    private LocalDate getDeltaBetweenTwoTime(String departureDate, String arrivalDate, String departureTime, String arrivalTime) {

        var currentDepartureDate = departureDate + " "  + departureTime;
        var currentArrivalDate = arrivalDate + " "  + arrivalTime;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");


        Date actualDepartureTime = null;
        Date actualArrivalTime = null;
        try {
            actualDepartureTime = dateFormat.parse(currentDepartureDate);
            actualArrivalTime = dateFormat.parse(currentArrivalDate);

            long diffInMillies = Math.abs(actualArrivalTime.getTime() - actualDepartureTime.getTime());
            int diffInDays = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            int diffInHours = (int) TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            int diffInMinutes = (int) TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);


            return LocalDate.of(diffInDays,diffInHours,(diffInMinutes % 60));
        } catch (ParseException e) {
           throw new RuntimeException("Не удалось распарсить дату и время");

        }
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

