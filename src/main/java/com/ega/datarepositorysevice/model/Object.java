package com.ega.datarepositorysevice.model;

import java.util.Date;
import java.util.List;

public class Object {
    String id;
    String name;
    int size;
    Date created;
    Date updated;
    String version;
    String mime_type; //TODO enums for mediatypes
    List<Checksum> checksums;
    List<AccessMethods> accessMethods;
    String description;
    List<String> aliaces;
}
