package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.impl.BundleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import com.sun.org.apache.xml.internal.utils.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class BundleServiceIntegrationTest {
    @Autowired
    private BundleService bundleService;

    @Autowired
    BundleRepository bundleRepository;
    private Bundle bundleTestObject;
    private Bundle unsavedBundleTestObject;



    @Before
    public void prepareDatabase() throws URI.MalformedURIException {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        bundleTestObject = new Bundle(1L, "string", 23, date,
                date, "string", null, "string",
                Arrays.asList("string"), Arrays.asList());
        BundleObject bundleObject = new BundleObject(null,"name", BundleObjectType.OBJECT
                , Arrays.asList(new URI("https://www.baeldung.com/junit-assert-exception"),new URI("https://www.baeldung.com/junit-assert-exception"))
                ,null);
        unsavedBundleTestObject = new Bundle(null, "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)), "string",
                Arrays.asList("string"), Arrays.asList(bundleObject));
    }
    @Test
    public void testSaving(){
        Mono<Bundle> bundleMethodsMono = bundleService.saveBundle(Mono.just(unsavedBundleTestObject));
        Bundle bundle = bundleMethodsMono.block();
        Assert.assertNotNull(bundle.getId());
        Assert.assertEquals(unsavedBundleTestObject, bundle);
    }



    @Test
    public void testUpdating(){
        Mono<Bundle> bundleMono = bundleService.saveBundle(Mono.just(unsavedBundleTestObject));
        Bundle bundle = bundleMono.block();
        bundle.setDescription("new description");
        bundleMono = bundleService.updateBundle(Mono.just(bundle));
        bundle = bundleMono.block();
        Assert.assertNotNull(bundle.getId());
        Assert.assertEquals(unsavedBundleTestObject, bundle);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testUpdatingNotFound(){
        unsavedBundleTestObject.setId(0L);
        Mono<Bundle> bundleMono = bundleService.updateBundle(Mono.just(unsavedBundleTestObject));
        bundleMono.block();
    }


    @Test
    public void testDeleting(){
        Mono<Bundle> bundleMono = bundleService.saveBundle(Mono.just(unsavedBundleTestObject));
        Bundle bundle = bundleMono.block();
        bundleService.deleteBundleById(bundle.getId()).block();
        Assert.assertFalse(bundleRepository.existsById(bundle.getId()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeletingError(){
        Mono<Void> booleanMono = bundleService.deleteBundleById(0L);
        booleanMono.block();
    }

    @Test
    public void testExistingValue() {
        Mono<Bundle> bundleMono = bundleService.saveBundle(Mono.just(unsavedBundleTestObject));
        Bundle bundle = bundleMono.block();
        bundleMono = bundleService.getBundleById(bundle.getId());
        Optional<Bundle> bundleOptional = bundleMono.blockOptional();
        Assert.assertTrue(bundleOptional.isPresent());
        Assert.assertEquals(unsavedBundleTestObject, bundleOptional.get());


    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testEmptyValue() {
        Mono<Bundle> bundleMono = bundleService.getBundleById(0L);
        bundleMono.block();
    }

}
