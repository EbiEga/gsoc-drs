package com.ega.datarepositorysevice.model.json_representation;

import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class ErrorJsonTest {

    @Autowired
    private JacksonTester<Error> json;

    @Test
    public void testSerialize() throws IOException {
        File file = ResourceUtils.getFile("classpath:model/error/error.json");

        Error error = new Error("msg", HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(json.write(error))
                .isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws IOException {
        File file = ResourceUtils.getFile("classpath:model/error/error.json");

        Error error = new Error("msg", HttpStatus.INTERNAL_SERVER_ERROR);
        Error actualError = json.parseObject(new String(Files.readAllBytes(file.toPath())));

        Assert.assertEquals(error,actualError);
    }

}
