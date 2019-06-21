package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AccessMethodsServiceIntegrationTest {

    @Autowired
    private AccessMethodsService accessMethodsService;

    @Autowired
    private AccessMethodsRepository accessMethodsRepository;



    private AccessMethods accessMethodsTestObject;

    @Before
    public void prepareDatabase() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        accessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        accessMethodsTestObject = accessMethodsRepository.save(accessMethodsTestObject);
    }

    @Test
    public void testExistingValue() {
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(accessMethodsTestObject.getAccessId());
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertTrue(accessMethodsOptional.isPresent());
        AccessMethods actual = accessMethodsOptional.get();
        Assert.assertEquals(accessMethodsTestObject, actual);


    }

    @Test
    public void testEmptyValue() {
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(accessMethodsTestObject.getAccessId()+1);
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertFalse(accessMethodsOptional.isPresent());
    }

}
