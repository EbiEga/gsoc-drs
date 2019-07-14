package com.ega.datarepositorysevice.model.json_representation;

import com.ega.datarepositorysevice.model.Object;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ObjectJsonTest {
    @Autowired
    private JacksonTester<Object> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/object/object_null.json");
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(Long.parseLong("1"), "string", 0, date, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);
        assertThat(json.write(object)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/object/object_null.json");
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(Long.parseLong("1"), "string", 0, date, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);

        Object parsedObject = json.parseObject(new String(Files.readAllBytes(file.toPath())));
        Assert.assertEquals(object, parsedObject);

    }
}
