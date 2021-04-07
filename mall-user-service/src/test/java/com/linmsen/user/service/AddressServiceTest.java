package com.linmsen.user.service;

import com.linmsen.UserApplication;
import com.linmsen.user.model.AddressDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    void details() {
        AddressDO addressDO = addressService.details(1);
        log.info(addressDO.toString());
        Assert.assertNotNull(addressDO);
    }
}