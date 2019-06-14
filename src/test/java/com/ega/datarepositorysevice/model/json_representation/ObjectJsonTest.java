package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.ServiceInfo;
import com.google.api.client.util.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class ObjectJsonTest {
    @Autowired
    private JacksonTester<Object> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/object/object_null.json");
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(Long.parseLong("1"), "string", 0, date,  date, "string", "application/json",
                null, null, "string", null);
        System.out.println(json.write(object));
        assertThat(json.write(object)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
    }
}
