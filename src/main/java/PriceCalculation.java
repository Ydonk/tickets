import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//- Разницу между средней ценой
// и медианой для полета между городами  Владивосток и Тель-Авив

public class PriceCalculation {
    private Map<String, List<Ticket>> stringListMap;


    public PriceCalculation() {
    }

    public Map<String,String> diffAvgAndMedian() {

        var resultMap = new HashMap<String,String>();
        var avg = calculateAvgPrice();
        var median = calculateMedian();



        for(var entry : median.entrySet()){
            var key = entry.getKey();
            var valMedian = entry.getValue();
            var valAvg = avg.get(key);
            resultMap.put(key, String.valueOf((valMedian - valAvg)));
        }
       return resultMap;
    }

    public void setStringListMap(Map<String, List<Ticket>> stringListMap) {
        this.stringListMap = stringListMap;

    }

    private Map<String, Double> calculateAvgPrice() {
        Map<String, Double> price = new HashMap<>();

        for (var entry : stringListMap.entrySet()) {
            var k = entry.getKey();
            var v = entry.getValue();
            var diff = v.stream()
                    .map(Ticket::getPrice)
                    .mapToInt(el -> el)
                    .summaryStatistics()
                    .getAverage();
            price.put(k, diff);

        }
        return price;
    }

    private Map<String, Double> calculateMedian() {
        Map<String, Double> resMap = new HashMap<>();

        for (var entry : stringListMap.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue().stream().map(Ticket::getPrice).sorted().collect(Collectors.toList());
            var size = value.size();

            if (value.size() % 2 == 0) {
                var val = (value.get(size / 2 - 1) + value.get(size / 2) / 2.0);
                resMap.put(key, val);
            } else {
                var val1 = Double.valueOf((value.get(size / 2)));
                resMap.put(key, val1);
            }
        }

        return resMap;
    }
}

