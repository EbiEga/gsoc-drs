package com.ega.datarepositorysevice.model;

import java.util.List;

public class AccessMethods {
    String access_id;
    String type; //TODO make enum
    String region; //TODO check format
    AccessURL access_url;

    protected class AccessURL{
        String url;
        List<String> headers;
    }

}
