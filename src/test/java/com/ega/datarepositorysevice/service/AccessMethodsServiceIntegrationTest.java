package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class AccessMethodsServiceIntegrationTest {

    @Autowired
    private AccessMethodsService accessMethodsService;

     @Autowired
    private AccessMethodsRepository accessMethodsRepository;
     @Autowired
     private ObjectRepository objectRepository;

    private AccessMethods savedAccessMethodsTestObject;
    private AccessMethods unsavedAccessMethodsTestObject;
    private Object containingObject;

    @Before
    public void prepareDatabase() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        savedAccessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        unsavedAccessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        containingObject = TestObjectCreator.getObject();
        containingObject = objectRepository.save(containingObject);
    }


    @Test
    public void testSaving(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertNotNull(accessMethods.getAccessId());
        Assert.assertEquals(unsavedAccessMethodsTestObject,accessMethods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSavingError(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(0L,Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertNotNull(accessMethods.getAccessId());
        Assert.assertEquals(unsavedAccessMethodsTestObject,accessMethods);
    }

    @Test
    public void testUpdating(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertNotNull(accessMethods.getAccessId());
        Assert.assertEquals(unsavedAccessMethodsTestObject,accessMethods);
        accessMethods.setRegion("new region");
        Mono<AccessMethods> updatedAccessMethodsMono = accessMethodsService.updateAccessMethod(containingObject.getId(),Mono.just(accessMethods));
        Assert.assertEquals(accessMethods,updatedAccessMethodsMono.block());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUpdatingObjectNotFound(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertNotNull(accessMethods.getAccessId());
        Assert.assertEquals(unsavedAccessMethodsTestObject,accessMethods);
        accessMethods.setRegion("new region");
        Mono<AccessMethods> updatedAccessMethodsMono = accessMethodsService.updateAccessMethod(0L,Mono.just(accessMethods));
        Assert.assertEquals(accessMethods,updatedAccessMethodsMono.block());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatingAccessNotFound(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertNotNull(accessMethods.getAccessId());
        Assert.assertEquals(unsavedAccessMethodsTestObject,accessMethods);
        accessMethods.setRegion("new region");
        accessMethods.setAccessId(0L);
        Mono<AccessMethods> updatedAccessMethodsMono = accessMethodsService.updateAccessMethod(containingObject.getId(),Mono.just(accessMethods));
        Assert.assertEquals(accessMethods,updatedAccessMethodsMono.block());
    }

    @Test
    public void testDeleting(){
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Assert.assertTrue(accessMethodsService.deleteById(containingObject.getId(),accessMethods.getAccessId()).block());
        Assert.assertFalse(accessMethodsRepository.existsById(unsavedAccessMethodsTestObject.getAccessId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeletingAccessMethodNotFound(){
        Mono<Boolean> mono = accessMethodsService.deleteById(containingObject.getId(),0L);
        mono.block();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeletingObjectNotFound(){
        Mono<Boolean> mono = accessMethodsService.deleteById(0L,0L);
        mono.block();
    }

    @Test
    public void testExistingValue() {
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(containingObject.getId(),Mono.just(unsavedAccessMethodsTestObject));
        AccessMethods accessMethods = accessMethodsMono.block();
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(containingObject.getId(),accessMethods.getAccessId());
        Optional<AccessMethods> accessMethodsOptional = accessMethod.blockOptional();

        Assert.assertTrue(accessMethodsOptional.isPresent());
        Assert.assertEquals(savedAccessMethodsTestObject, accessMethodsOptional.get());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyValue() {
        Mono<AccessMethods> accessMethod = accessMethodsService.getAccessMethodsById(containingObject.getId(), 0L);
        accessMethod.block();

    }

}
