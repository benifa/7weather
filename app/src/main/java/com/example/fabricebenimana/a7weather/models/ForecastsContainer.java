package com.example.fabricebenimana.a7weather.models;


import com.example.fabricebenimana.a7weather.bus.event.BaseResponse;

import java.util.List;

public class ForecastsContainer extends BaseResponse {
    private City city;
    private int cnt;
    private String cod;
    private double message;
    private List<ListItem> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return
                "ForecastsContainer{" +
                        "city = '" + city + '\'' +
                        ",cnt = '" + cnt + '\'' +
                        ",cod = '" + cod + '\'' +
                        ",message = '" + message + '\'' +
                        ",list = '" + list + '\'' +
                        "}";
    }
}