package com.aplimovil.infobus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RutaItem {
    String route;
    Date created;
    public String getRoute() {
        return route;
    }
    public Date getCreated() {
        return created;
    }
    public RutaItem(String _route) {
        this(_route, new Date(java.lang.System.currentTimeMillis()));
    }
    public RutaItem(String _route, Date _created) {
        route = _route;
        created = _created;
    }
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(created);
        return "(" + dateString + ") " + route;
    }
}