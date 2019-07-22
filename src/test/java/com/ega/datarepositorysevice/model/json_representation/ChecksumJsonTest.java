package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ChecksumJsonTest {
    @Autowired
    private JacksonTester<Checksum> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/checksum/checksum_valid.json");
        Checksum checksum = new Checksum("string", ChecksumType.MD5_Code);
        assertThat(json.write(checksum)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/checksum/checksum_valid.json");
        Checksum checksum = new Checksum("string", ChecksumType.MD5_Code);

        Checksum parsedChecksum = json.parseObject(new String(Files.readAllBytes(file.toPath())));
        assertThat(parsedChecksum).isEqualTo(checksum);
    }
}
