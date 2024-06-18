import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//- Разницу между средней ценой
// и медианой для полета между городами  Владивосток и Тель-Авив

public class PriceCalculation {
    private List<Ticket> stringLis;


    public PriceCalculation() {
    }

    public Double diffAvgAndMedian() {
        var avg = calculateAvgPrice();
        var median = calculateMedian();

        return Math.abs(median - avg);
    }

    public void setStringListMap(List<Ticket> ticketList) {
        this.stringLis = ticketList;

    }

    private Double calculateAvgPrice() {
        var sum =  stringLis.stream().map(Ticket::getPrice)
                .mapToDouble(el -> el)
                .sum();

        return  (sum / stringLis.size());
    }

    private Double calculateMedian() {
        int size = stringLis.size();
        var value = stringLis.stream().map(Ticket::getPrice).sorted().collect(Collectors.toList());
        value.forEach(System.out::println);
        if (value.size() % 2 == 0) {
            return  (value.get(value.size() / 2 - 1) + value.get(value.size() / 2) / 2.0);

        } else {
            return Double.valueOf((value.get(size / 2)));
        }

    }
}

