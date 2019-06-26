package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.Object;
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
import java.util.Arrays;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class BundleJsonTest {
    @Autowired
    private JacksonTester<Bundle> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/bundle/bundle_valid.json");
        LocalDateTime testDateTime = LocalDateTime.of(2018,12,12,12,12,12,121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle bundle = new Bundle("string","string",23,date,
                date, "string", null,"string",
                Arrays.asList("string"), Arrays.asList(new BundleObject("string", "string", "object",null, null)) );
        System.out.println(json.write(bundle));
        assertThat(json.write(bundle)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
    }
}

