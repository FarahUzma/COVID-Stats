package m.covidstat;

import android.content.res.TypedArray;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StatisticsModel implements Serializable {

    @SerializedName("get")
    private String get;

    @SerializedName("parameters")
    private List<String> parameters;

    @SerializedName("errors")
    private List<String> errors;

    @SerializedName("results")
    private int results;

    @SerializedName("response")
    private List<Response> response;

    private TypedArray imageResource;


    public TypedArray getImageResource() {
        return imageResource;
    }

    public void setImageResource(TypedArray imageResource) {
        this.imageResource = imageResource;
    }


    public void setGet(String get){
        this.get = get;
    }
    public String getGet(){
        return this.get;
    }
    public void setParameters(List<String> parameters){
        this.parameters = parameters;
    }
    public List<String> getParameters(){
        return this.parameters;
    }
    public void setErrors(List<String> errors){
        this.errors = errors;
    }
    public List<String> getErrors(){
        return this.errors;
    }
    public void setResults(int results){
        this.results = results;
    }
    public int getResults(){
        return this.results;
    }
    public void setResponse(List<Response> response){
        this.response = response;
    }
    public List<Response> getResponse(){
        return this.response;
    }

    public List<Response> getSubList(int itemCount){
        List<Response> responseList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++ ){
            if (itemCount + i < 224) {
                responseList.add(response.get((itemCount == 0 ?
                        (itemCount + 1 + i) : (itemCount + i))));
            }

        }

        return responseList;
    }

    public class Cases implements Serializable
    {
        @SerializedName("new")
        private String newCases;

        @SerializedName("active")
        private int active;

        @SerializedName("critical")
        private int critical;

        @SerializedName("recovered")
        private int recovered;

        @SerializedName("total")
        private int total;

        public void setNew(String newCases){
            this.newCases = newCases;
        }
        public String getNew(){
            return this.newCases == null ? "0" : newCases;
        }
        public void setActive(int active){
            this.active = active;
        }
        public int getActive(){
            return this.active;
        }
        public void setCritical(int critical){
            this.critical = critical;
        }
        public int getCritical(){
            return this.critical;
        }
        public void setRecovered(int recovered){
            this.recovered = recovered;
        }
        public int getRecovered(){
            return this.recovered;
        }
        public void setTotal(int total){
            this.total = total;
        }
        public int getTotal(){
            return this.total;
        }
    }

    public class Deaths implements Serializable
    {
        @SerializedName("new")
        private String newDeaths;

        @SerializedName("total")
        private int total;

        public void setNew(String newDeaths){
            this.newDeaths = newDeaths;
        }
        public String getNew(){
            return this.newDeaths== null ? "0" : newDeaths;
        }
        public void setTotal(int total){
            this.total = total;
        }
        public int getTotal(){
            return this.total;
        }
    }

    public class Tests implements Serializable
    {
        @SerializedName("total")
        private String total;

        public void setTotal(String total){
            this.total = total;
        }
        public String getTotal(){
            return this.total;
        }
    }

    public static class Response implements Serializable
    {
        @SerializedName("country")
        private String country;

        @SerializedName("cases")
        private Cases cases;

        @SerializedName("deaths")
        private Deaths deaths;

        @SerializedName("tests")
        private Tests tests;

        @SerializedName("day")
        private String day;

        @SerializedName("time")
        private String time;


        public void setCountry(String country){
            this.country = country;
        }
        public String getCountry(){
            return this.country;
        }
        public void setCases(Cases cases){
            this.cases = cases;
        }
        public Cases getCases(){
            return this.cases;
        }
        public void setDeaths(Deaths deaths){
            this.deaths = deaths;
        }
        public Deaths getDeaths(){
            return this.deaths;
        }
        public void setTests(Tests tests){
            this.tests = tests;
        }
        public Tests getTests(){
            return this.tests;
        }
        public void setDay(String day){
            this.day = day;
        }
        public String getDay(){
            return this.day;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }


    }


}
