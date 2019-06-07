package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Bundle;
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
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AccessMethodRepositoryTest {
    @Autowired
    AccessMethodsRepository accessMethodsRepository;

    @Test
    public void getBundleByIdTest() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com",map);
        AccessMethods accessMethods = new AccessMethods("access_id","s3","region",null, null);
        accessMethodsRepository.save(accessMethods);
        AccessMethods responseAccessMethods = accessMethodsRepository.findById("access_id").get();
        Assert.assertEquals("access_id", responseAccessMethods.getAccess_id());
        Assert.assertEquals("region", responseAccessMethods.getRegion());
        Assert.assertEquals("s3", responseAccessMethods.getType());
    }
}
