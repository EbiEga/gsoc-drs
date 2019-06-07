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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        Object object = new Object("id", "string", 0, null,  df.parse("2019-06-02T14:04:49.123Z"), "string", "application/json",
                null, null, "string", null);
        System.out.println(json.write(object));
        assertThat(json.write(object)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
    }
}
