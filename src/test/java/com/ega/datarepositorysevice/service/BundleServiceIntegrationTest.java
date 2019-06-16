package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.impl.BundleServiceImpl;
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
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class BundleServiceIntegrationTest {
    private BundleService bundleService;
    private Bundle bundleTestObject;


    @Before
    public void prepareDatabase() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        bundleTestObject = new Bundle(1L, "string", 23, date,
                date, "string", null, "string",
                Arrays.asList("string"), Arrays.asList());

        BundleRepository bundleRepository = mock(BundleRepository.class);
        when(bundleRepository.findById(1L)).thenReturn(Optional.of(bundleTestObject));
        when(bundleRepository.findById(2L)).thenReturn(Optional.empty());

        bundleService = new BundleServiceImpl(bundleRepository);

    }

    @Test
    public void testExistingValue() {
        Mono<Bundle> bundleMono = bundleService.getBundletById(1L);
        Optional<Bundle> bundleOptional = bundleMono.blockOptional();

        Assert.assertTrue(bundleOptional.isPresent());
        Assert.assertEquals(bundleTestObject, bundleOptional.get());


    }

    @Test
    public void testEmptyValue() {
        Mono<Bundle> bundleMono = bundleService.getBundletById(2L);
        Optional<Bundle> bundleOptional = bundleMono.blockOptional();

        Assert.assertFalse(bundleOptional.isPresent());
    }

}
