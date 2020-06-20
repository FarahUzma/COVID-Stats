package m.covidstat;

import android.content.res.TypedArray;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndiaModel {



    @SerializedName("statewise")
    private List<Deaths> deaths;


    public List<Deaths> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Deaths> deaths) {
        this.deaths = deaths;
    }

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



    public class Deaths implements Serializable
    {

        @SerializedName("active")
        private String active;

        @SerializedName("state")
        private String state;

        @SerializedName("deaths")
        private String deaths;
        @SerializedName("recovered")
        private String recovered;

        public String getRecovered() {
            return recovered;
        }

        public void setRecovered(String recovered) {
            this.recovered = recovered;
        }

        public String getDeaths() {
            return deaths;
        }

        public void setDeaths(String deaths) {
            this.deaths = deaths;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
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

        @SerializedName("active")
        private String active;

        @SerializedName("state")
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }


    }


}
