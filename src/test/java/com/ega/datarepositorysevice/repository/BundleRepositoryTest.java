package com.ega.datarepositorysevice.repository;


import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class BundleRepositoryTest {

    @Autowired
    BundleRepository bundleRepository;

    @Test
    public void getBundleByIdTest() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        Bundle bundle = new Bundle("string","string",23,df.parse("2019-06-02T14:04:49.123Z"),
                df.parse("2019-06-02T14:04:49.123Z"), "string", null,"string",
                Arrays.asList("string"), Arrays.asList() );

        bundleRepository.save(bundle);
        Bundle responseBundle = bundleRepository.findById("string").get();
        Assert.assertEquals("string", responseBundle.getId());
        Assert.assertEquals("string", responseBundle.getName());
        Assert.assertEquals(23, responseBundle.getSize());
    }
}
