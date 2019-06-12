package com.ega.datarepositorysevice.model.json_representation.date;


import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Object;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class DateRFC3339Test {
    @Autowired
    private JacksonTester<Object> jsonObjectMapper;

    @Autowired
    private JacksonTester<Bundle> jsonBundleMapper;

    @Test
    public void testObjectSerializeDateTime() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object("id", "string", 0, date, null, "string", "application/jsonObjectMapper",
                null, null, "string", null);
        System.out.println(jsonObjectMapper.write(object));
        assertThat(jsonObjectMapper.write(object))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:00+02:00");
    }

    @Test
    public void testObjectSerializeDateTimeWithNanoSec() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object("id", "string", 0, date, date, "string", "application/jsonObjectMapper",
                null, null, "string", null);
        System.out.println(jsonObjectMapper.write(object));
        assertThat(jsonObjectMapper.write(object))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:12.1212+02:00");
        assertThat(jsonObjectMapper.write(object))
                .extractingJsonPathStringValue("updated")
                .isEqualTo("2018-12-12T12:12:12.1212+02:00");
    }


    @Test
    public void testObjectDeserializeCreatedDate() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        String jsonString = "{ \"created\": \"2018-12-12T12:12:12.1212+02:00\"}";
        Object object = jsonObjectMapper.parseObject(jsonString);
        Assert.assertTrue(object.getCreated().isEqual(date));
    }

    @Test
    public void testObjectDeserializeUpdatedDate() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        String jsonString = "{ \"updated\": \"2018-12-12T12:12:12.1212+02:00\"}";
        Object object = jsonObjectMapper.parseObject(jsonString);
        Assert.assertTrue(object.getUpdated().isEqual(date));
    }


    @Test
    public void testBundleSerializeDateTime() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle bundle = new Bundle("string","string",23,date,
                date, "string", null,"string",
                null,null );
        assertThat(jsonBundleMapper.write(bundle))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:00+02:00");
        assertThat(jsonBundleMapper.write(bundle))
                .extractingJsonPathStringValue("updated")
                .isEqualTo("2018-12-12T12:12:00+02:00");
    }

    @Test
    public void testBundleSerializeDateTimeWithNanoSec() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle bundle = new Bundle("string","string",23,date,
                date, "string", null,"string",
                null, null );
        System.out.println(jsonBundleMapper.write(bundle));
        assertThat(jsonBundleMapper.write(bundle))
                .extractingJsonPathStringValue("created")
                .isEqualTo("2018-12-12T12:12:12.1212+02:00");
        assertThat(jsonBundleMapper.write(bundle))
                .extractingJsonPathStringValue("updated")
                .isEqualTo("2018-12-12T12:12:12.1212+02:00");
    }


    @Test
    public void testBundleDeserializeCreatedDate() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        String jsonString = "{ \"created\": \"2018-12-12T12:12:12.1212+02:00\"}";
        Bundle bundle = jsonBundleMapper.parseObject(jsonString);
        Assert.assertTrue(bundle.getCreated().isEqual(date));
    }

    @Test
    public void testBundleDeserializeUpdatedDate() throws IOException {
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        String jsonString = "{ \"updated\": \"2018-12-12T12:12:12.1212+02:00\"}";
        Bundle bundle = jsonBundleMapper.parseObject(jsonString);
        Assert.assertTrue(bundle.getUpdated().isEqual(date));
    }
}
