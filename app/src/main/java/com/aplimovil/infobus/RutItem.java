package com.aplimovil.infobus;


import java.text.SimpleDateFormat;
import java.util.Date;

public class RutItem {
    String rute;
    Date created;
    public String getRute() {
        return rute;
    }
    public Date getCreated() {
        return created;
    }
    public RutItem(String _rute) {
        this(_rute, new Date(java.lang.System.currentTimeMillis()));
    }
    public RutItem(String _rute, Date _created) {
        rute = _rute;
        created = _created;
    }
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(created);
        return "(" + dateString + ") " + rute;
    }
}