package m.covidstat;

import io.reactivex.functions.Predicate;

public class SearchFilter {

    public static Predicate<StatisticsModel.Response> searchFilter(String filter){
        return response -> {
            if (filter == null || filter.length() == 0) {
                return false;
            }
            return response!=null && response.getCountry()!=null && response.getCountry().toLowerCase().trim().contains(filter.toLowerCase().trim());
        };
    }


}
