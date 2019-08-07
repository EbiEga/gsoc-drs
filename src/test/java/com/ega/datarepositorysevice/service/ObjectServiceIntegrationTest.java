package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.*;
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
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class ObjectServiceIntegrationTest {

    @Autowired
    private ObjectService objectService;

    @Autowired
    private ObjectRepository objectRepository;

    private Object objectTestObject;
    private Object unsavedObjectTestObject;


    @Before
    public void prepareDatabase() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        AccessMethods accessMethodsTestObject = new AccessMethods( null, AccessMethodType.S3, "region", accessURL);

        objectTestObject = new Object(null,"string", 0, date, date, "string", "application/json",
                Arrays.asList(new Checksum("s342ing", ChecksumType.MD5_Code)), Arrays.asList(accessMethodsTestObject), "string", null);

        unsavedObjectTestObject = new Object( null,"string", 0, date, date, "string", "application/json",
                Arrays.asList(new Checksum("s342ing", ChecksumType.MD5_Code)), Arrays.asList(accessMethodsTestObject), "string", null);

    }

    @Test
    public void testSaving(){
        Mono<Object> objectMethodsMono = objectService.saveObject(Mono.just(unsavedObjectTestObject));
        Object object = objectMethodsMono.block();
        Assert.assertNotNull(object.getId());
        Assert.assertEquals(unsavedObjectTestObject, object);
    }

    @Test
    public void testUpdating(){
        Mono<Object> objectMethodsMono = objectService.saveObject(Mono.just(unsavedObjectTestObject));
        Object object = objectMethodsMono.block();
        Assert.assertNotNull(object);
        object.setDescription("new description");
        Mono<Object> objectMono = objectService.updateObject(Mono.just(object));
        Assert.assertEquals(object,objectMono.block());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testUpdatingNotFound(){
        unsavedObjectTestObject.setId(0L);
        Mono<Object> objectMono = objectService.updateObject(Mono.just(unsavedObjectTestObject));
        objectMono.block();
    }

    @Test
    public void testDeleting(){
        Mono<Object> objectMethodsMono = objectService.saveObject(Mono.just(unsavedObjectTestObject));
        Object object = objectMethodsMono.block();
        Assert.assertNotNull(object);
        objectService.deleteObjectById(object.getId());
        Assert.assertFalse(objectRepository.existsById(object.getId()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeletingError(){
        Mono<Void> mono = objectService.deleteObjectById(0L);
        mono.block();

    }

    @Test
    public void testExistingValue() {
        Mono<Object> objectMethodsMono = objectService.saveObject(Mono.just(unsavedObjectTestObject));
        Optional<Object> optionalObject = objectMethodsMono.blockOptional();
        Assert.assertTrue(optionalObject.isPresent());
        Object actualObject = optionalObject.get();
        Assert.assertEquals(objectTestObject, actualObject);

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testEmptyValue() {
        Mono<Object> objectMono = objectService.getObjectById(0L);
        Optional<Object> objectOptional = objectMono.blockOptional();

    }
}
