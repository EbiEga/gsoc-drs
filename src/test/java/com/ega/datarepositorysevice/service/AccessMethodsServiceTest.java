package com.ega.datarepositorysevice.service;


import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.impl.AccessMethodsServiceImpl;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.dao.EmptyResultDataAccessException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessMethodsServiceTest {
    private AccessMethodsService accessMethodsService;
    private AccessMethods accessMethodsTestObjectWithId;


    @Before
    public void prepareDatabase() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        accessMethodsTestObjectWithId = new AccessMethods(1L, AccessMethodType.S3, "region", accessURL);
        AccessMethodsRepository accessMethodsRepository = mock(AccessMethodsRepository.class);
        when(accessMethodsRepository.findById(1L)).thenReturn(Optional.of(accessMethodsTestObjectWithId));
        when(accessMethodsRepository.findById(2L)).thenReturn(Optional.empty());

        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object objectTestObject = new Object(1L, "string", 0, null, date, "string", "application/json",
                new ArrayList<>(), Arrays.asList(accessMethodsTestObjectWithId), "string", null);

        ObjectRepository objectRepository = mock(ObjectRepository.class);
        when(objectRepository.findById(1L)).thenReturn(Optional.of(objectTestObject));
        when(objectRepository.findById(2L)).thenReturn(Optional.empty());
        when(objectRepository.existsById(1L)).thenReturn(true);
        accessMethodsService = new AccessMethodsServiceImpl(accessMethodsRepository, objectRepository);
    }

    @Test
    public void testExistingValue() {
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(1L,1L);
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertTrue(accessMethodsOptional.isPresent());
        Assert.assertEquals(accessMethodsTestObjectWithId, accessMethodsOptional.get());


    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testEmptyValue() {
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(1L, 2L);
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertFalse(accessMethodsOptional.isPresent());
    }

}
