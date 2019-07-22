package com.ega.datarepositorysevice.repository;


import com.ega.datarepositorysevice.model.Bundle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class BundleRepositoryTest {

    @Autowired
    BundleRepository bundleRepository;

    @Test
    public void getBundleByIdTest() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle bundle = new Bundle(1L, "string", 23, date,
                date, "string", null, "string",
                Arrays.asList("string"), Arrays.asList());

        bundle = bundleRepository.save(bundle);
        Bundle responseBundle = bundleRepository.findById(bundle.getId()).get();
        Assert.assertEquals(bundle.getId(), responseBundle.getId());
        Assert.assertEquals("string", responseBundle.getName());
        Assert.assertEquals(23, responseBundle.getSize());
    }

    @Test
    public void getBundleByIdNullTest() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle bundle = new Bundle(Long.parseLong("5"), "string", 23, date,
                date, "string", null, "string",
                Arrays.asList("string"), Arrays.asList());

        bundleRepository.save(bundle);
        Optional<Bundle> responseBundle = bundleRepository.findById(3L);
        Assert.assertFalse(responseBundle.isPresent());
    }
}
