package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class ObjectServiceIntegrationTest {

    @Autowired
    private ObjectService objectService;

    @Autowired
    private ObjectRepository objectRepository;

    private Object objectTestObject;

    @Before
    public void prepareDatabase() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        AccessMethods accessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);

        objectTestObject = new Object(null, "string", 0, date, date, "string", "application/json",
                Arrays.asList(new Checksum("s342ing", ChecksumType.MD5_Code)), Arrays.asList(accessMethodsTestObject), "string", null);

        objectTestObject = objectRepository.save(objectTestObject);

    }

    @Test
    public void testExistingValue() {
        Mono<Object> objectMono = objectService.getObjectById(objectTestObject.getId());
        Optional<Object> optionalObject = objectMono.blockOptional();
        Assert.assertTrue(optionalObject.isPresent());
        Object actualObject = optionalObject.get();
        Assert.assertEquals(objectTestObject, actualObject);

    }

    @Test
    public void testEmptyValue() {
        Mono<Object> bundleMono = objectService.getObjectById(2L);
        Optional<Object> bundleOptional = bundleMono.blockOptional();

        Assert.assertFalse(bundleOptional.isPresent());
    }
}
