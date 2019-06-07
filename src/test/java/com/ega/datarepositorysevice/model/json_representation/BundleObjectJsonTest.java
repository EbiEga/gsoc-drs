package com.ega.datarepositorysevice.model.json_representation;


import com.ega.datarepositorysevice.model.AccessMethods;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class BundleObjectJsonTest {
    @Autowired
    private JacksonTester<BundleObject> json;

    @Test
    public void testSerialize() throws Exception {
        File file = ResourceUtils.getFile("classpath:model/bundle_object/bundle_object_valid.json");
        BundleObject bundleObject = new BundleObject("id", "name", "object",null, null) ;
        assertThat(json.write(bundleObject)).isEqualToJson(file);
    }

    @Test
    public void testDeserialize() throws Exception {
    }
}
