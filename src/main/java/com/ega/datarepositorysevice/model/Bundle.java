package com.ega.datarepositorysevice.model;

import java.util.Date;
import java.util.List;

public class Bundle {
    String id;
    String name;
    int size;
    Date created;
    Date updated;
    String version;
    List<Checksum> checksum;
    String description;
    List<String> aliases;
    List<BundleObject> contents;

}
