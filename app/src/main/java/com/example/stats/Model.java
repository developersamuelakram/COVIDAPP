package com.example.stats;

public class Model {

    String todaycases, totalcases, countryname, flag, recovered, deaths;

    public Model(String todaycases, String totalcases, String countryname, String flag, String recovered, String deaths) {
        this.todaycases = todaycases;
        this.totalcases = totalcases;
        this.countryname = countryname;
        this.flag = flag;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public String getTodaycases() {
        return todaycases;
    }

    public String getTotalcases() {
        return totalcases;
    }

    public String getCountryname() {
        return countryname;
    }

    public String getFlag() {
        return flag;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }
}
