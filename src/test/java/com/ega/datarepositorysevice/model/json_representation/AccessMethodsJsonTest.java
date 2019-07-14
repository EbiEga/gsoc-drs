package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.DataRepositoryServiceApplication;
import com.ega.datarepositorysevice.controller.Router;
import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class AccessMethodsJsonTest {
    @Autowired
    private JacksonTester<AccessMethods> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/access_methods/access_methods_valid.json");
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods accessMethods = new AccessMethods(Long.parseLong("1"), AccessMethodType.S3, "region", accessURL);
        System.out.println(json.write(accessMethods));
        assertThat(json.write(accessMethods)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/access_methods/access_methods_valid.json");
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods accessMethods = new AccessMethods(Long.parseLong("1"), AccessMethodType.S3, "region", accessURL);

        AccessMethods parsedAccessMethods = json.parseObject(new String(Files.readAllBytes(file.toPath())));
        assertThat(parsedAccessMethods).isEqualTo(accessMethods);
    }
}
