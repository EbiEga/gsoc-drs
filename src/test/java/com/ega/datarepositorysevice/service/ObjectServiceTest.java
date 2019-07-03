package com.ega.datarepositorysevice.service;


import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.impl.ObjectServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ObjectServiceTest {

    private ObjectService objectService;
    private Object objectTestObject;

    @Before
    public void prepareDatabase() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        objectTestObject = new Object(1L, "string", 0, null, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);

        ObjectRepository repository = mock(ObjectRepository.class);
        when(repository.findById(1L)).thenReturn(Optional.of(objectTestObject));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        objectService = new ObjectServiceImpl(repository);

    }

    @Test
    public void testExistingValue() {
        Mono<Object> objectMono = objectService.getObjectById(1L);
        Optional<Object> objectOptional = objectMono.blockOptional();

        Assert.assertTrue(objectOptional.isPresent());
        Assert.assertEquals(objectTestObject, objectOptional.get());

    }

    @Test
    public void testEmptyValue() {
        Mono<Object> bundleMono = objectService.getObjectById(2L);
        Optional<Object> bundleOptional = bundleMono.blockOptional();

        Assert.assertFalse(bundleOptional.isPresent());
    }
}
