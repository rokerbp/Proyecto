package com.aplimovil.infobus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RutaItem {
    String route;
    String company;
    String places;
    String tme;
    Date created;
    public String getRoute() {
        return route;
    }
    public String getCompany() {
        return company;
    }
    public String getPlaces() {
        return places;
    }
    public String getTme() {
        return tme;
    }
    public Date getCreated() {
        return created;
    }
    public RutaItem(String _route, String _company, String _places, String _tme) {
        this(_route, _company, _places, _tme,  new Date(java.lang.System.currentTimeMillis()));
    }
    public RutaItem(String _route, String _company, String _places, String _tme, Date _created) {
        route = _route;
        company = _company;
        places = _places;
        tme = _tme;
        created = _created;
    }
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(created);
        return "(" + dateString + ") " + route + company + places + tme;
    }
}