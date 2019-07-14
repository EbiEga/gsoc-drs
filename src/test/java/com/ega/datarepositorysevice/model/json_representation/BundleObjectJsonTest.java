package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
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
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class BundleObjectJsonTest {
    @Autowired
    private JacksonTester<BundleObject> json;

    //TODO change test and change data to tet drs_uri list serialization
    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/bundle_object/bundle_object_valid.json");
        BundleObject bundleObject = new BundleObject(Long.parseLong("1"), "name", BundleObjectType.OBJECT, new ArrayList<>(), null);
        assertThat(json.write(bundleObject)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/bundle_object/bundle_object_valid.json");
        BundleObject bundleObject = new BundleObject(Long.parseLong("1"), "name", BundleObjectType.OBJECT, new ArrayList<>(), null);

        BundleObject parsedBundleObject = json.parseObject(new String(Files.readAllBytes(file.toPath())));
        assertThat(parsedBundleObject).isEqualTo(bundleObject);
    }
}
