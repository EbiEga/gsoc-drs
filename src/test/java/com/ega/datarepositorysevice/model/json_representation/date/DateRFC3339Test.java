package com.ega.datarepositorysevice.model.json_representation.date;


import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Object;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class DateRFC3339Test {
    @Autowired
    private JacksonTester<Object> json;

    @Test
    public void testSerializeDateTime() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object("id", "string", 0, date, null, "string", "application/json",
                null, null, "string", null);
        System.out.println(json.write(object));
        assertThat(json.write(object))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:00+02:00");
    }

    @Test
    public void testSerializeDateTimeWithNanoSec() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object("id", "string", 0, date, null, "string", "application/json",
                null, null, "string", null);
        System.out.println(json.write(object));
        assertThat(json.write(object))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:12.1212+02:00");
    }


    @Test
    public void testDeserializeDate() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        String jsonString = "{ \"created\": \"2018-12-12T12:12:12.1212+02:00\"}";
        Object object = json.parseObject(jsonString);
        Assert.assertTrue(object.getCreated().isEqual(date));
    }
}
