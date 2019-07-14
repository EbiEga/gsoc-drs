package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")

public class AccessMethodRepositoryTest {
    @Autowired
    AccessMethodsRepository accessMethodsRepository;

    @Test
    public void getBundleByIdTest() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods accessMethods = new AccessMethods(1L, AccessMethodType.S3, "region", accessURL);
        accessMethods = accessMethodsRepository.save(accessMethods);
        AccessMethods responseAccessMethods = accessMethodsRepository.findById(accessMethods.getAccessId()).get();
        Assert.assertEquals(accessMethods.getAccessId(), responseAccessMethods.getAccessId());
        Assert.assertEquals("region", responseAccessMethods.getRegion());
        Assert.assertEquals("s3", responseAccessMethods.getType());
    }

    @Test
    public void getBundleByIdNullTest() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods accessMethods = new AccessMethods(Long.parseLong("5"), AccessMethodType.S3, "region", accessURL);
        accessMethodsRepository.save(accessMethods);
        Optional<AccessMethods> responseAccessMethods = accessMethodsRepository.findById(Long.parseLong("4"));
        Assert.assertFalse(responseAccessMethods.isPresent());
    }
}
