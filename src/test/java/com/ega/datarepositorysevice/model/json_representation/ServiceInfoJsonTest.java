package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.ServiceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ServiceInfoJsonTest {
    @Autowired
    private JacksonTester<ServiceInfo> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/service_info/service_info_valid.json");
        ServiceInfo serviceInfo = new ServiceInfo("string", "string", "string", "string", "string");
        assertThat(json.write(serviceInfo)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/service_info/service_info_valid.json");
        ServiceInfo serviceInfo = new ServiceInfo("string", "string", "string", "string", "string");
        ObjectContent<ServiceInfo> parsedServiceInfo = json.parse(new String(Files.readAllBytes(file.toPath())));
        assertThat(parsedServiceInfo).isEqualTo(serviceInfo);
    }
}
