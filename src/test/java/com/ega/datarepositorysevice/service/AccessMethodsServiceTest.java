package com.ega.datarepositorysevice.service;


import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.service.impl.AccessMethodsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessMethodsServiceTest {

    private AccessMethodsService accessMethodsService;
    private AccessMethods accessMethodsTestObject;

    @Before
    public void prepareDatabase(){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        accessMethodsTestObject = new AccessMethods(1L, AccessMethodType.S3, "region", accessURL, null);

        AccessMethodsRepository accessMethodsRepository = mock(AccessMethodsRepository.class);
        when(accessMethodsRepository.findById(1L)).thenReturn(Optional.of(accessMethodsTestObject));
        when(accessMethodsRepository.findById(2L)).thenReturn(Optional.empty());

        accessMethodsService = new AccessMethodsServiceImpl(accessMethodsRepository);
    }

    @Test
    public void testExistingValue(){
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(1L);
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertTrue(accessMethodsOptional.isPresent());
        Assert.assertEquals(accessMethodsTestObject,accessMethodsOptional.get());


    }

    @Test
    public void testEmptyValue(){
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(2L);
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertFalse(accessMethodsOptional.isPresent());
    }

}
