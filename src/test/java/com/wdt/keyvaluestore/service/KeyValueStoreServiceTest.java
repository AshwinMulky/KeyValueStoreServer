package com.wdt.keyvaluestore.service;

import com.wdt.keyvaluestore.KeyValueStoreApplication;
import com.wdt.keyvaluestore.service.dto.KeyValueDTO;
import com.wdt.keyvaluestore.store.KeyValueStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KeyValueStoreApplication.class)
public class KeyValueStoreServiceTest {

    @Autowired
    private KeyValueStore keyValueStore;

    @Autowired
    private KeyValueStoreService keyValueStoreService;

    @Before
    public void setUp() {
        keyValueStore.create("1","ABC");
        keyValueStore.create("2","DEF");
    }

    @Test
    public void getKeyValue() {
        KeyValueDTO keyValue = keyValueStoreService.getKeyValue("1");
        assertThat(keyValue.getKey()).isEqualTo("1");
        assertThat(keyValue.getValue()).isEqualTo("ABC");
    }

    @Test
    public void getAllKeyValue() {
        List<KeyValueDTO> allKeyValue = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValue).isNotNull();
        assertThat(allKeyValue.size()).isEqualTo(2);
        assertThat(allKeyValue.get(0).getValue()).isEqualTo("ABC");
        assertThat(allKeyValue.get(1).getValue()).isEqualTo("DEF");
    }

    @Test
    public void getAllValues() {
        List<String> allValues = keyValueStoreService.getAllValues();
        assertThat(allValues.size()).isEqualTo(2);
    }

    @Test
    public void createKeyValue() {
        KeyValueDTO dto = keyValueStoreService.createKeyValue(new KeyValueDTO("3", "GHI"));
        assertThat(dto.getKey()).isEqualTo("3");
        assertThat(dto.getValue()).isEqualTo("GHI");

        List<KeyValueDTO> allKeyValue = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValue).isNotNull();
        assertThat(allKeyValue.size()).isEqualTo(3);
        assertThat(allKeyValue.get(0).getValue()).isEqualTo("ABC");
        assertThat(allKeyValue.get(1).getValue()).isEqualTo("DEF");
        assertThat(allKeyValue.get(2).getValue()).isEqualTo("GHI");
    }

    @Test
    public void createAllKeyValue() {
        List<KeyValueDTO> dtoList = new ArrayList<>();
        dtoList.add(new KeyValueDTO("3", "GHI"));
        dtoList.add(new KeyValueDTO("4", "JKL"));
        List<KeyValueDTO> allKeyValue = keyValueStoreService.createAllKeyValue(dtoList);
        assertThat(allKeyValue.size()).isEqualTo(2);

        List<KeyValueDTO> allKeyValues = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValues).isNotNull();
        assertThat(allKeyValues.size()).isEqualTo(4);
        assertThat(allKeyValues.get(0).getValue()).isEqualTo("ABC");
        assertThat(allKeyValues.get(1).getValue()).isEqualTo("DEF");
        assertThat(allKeyValues.get(2).getValue()).isEqualTo("GHI");
        assertThat(allKeyValues.get(3).getValue()).isEqualTo("JKL");
    }

    @Test
    public void updateKeyValue() {
        String oldValue = keyValueStoreService.updateKeyValue(new KeyValueDTO("1", "AAA"));
        assertThat(oldValue).isEqualTo("ABC");

        KeyValueDTO keyValue = keyValueStoreService.getKeyValue("1");
        assertThat(keyValue.getKey()).isEqualTo("1");
        assertThat(keyValue.getValue()).isEqualTo("AAA");
    }

    @Test
    public void deleteKeyValue() {
        KeyValueDTO keyValueDTO = keyValueStoreService.deleteKeyValue("2");
        assertThat(keyValueDTO).isNotNull();
        assertThat(keyValueDTO.getKey()).isEqualTo("2");
        assertThat(keyValueDTO.getValue()).isEqualTo("DEF");

        KeyValueDTO keyValue = keyValueStoreService.getKeyValue("2");
        assertThat(keyValue.getKey()).isEqualTo("2");
        assertThat(keyValue.getValue()).isNull();

        List<KeyValueDTO> allKeyValues = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValues).isNotNull();
        assertThat(allKeyValues.size()).isEqualTo(1);
    }

    @Test
    public void deleteAllKeyValue() {
        List<KeyValueDTO> allKeyValuesBeforeDelete = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValuesBeforeDelete).isNotNull();
        assertThat(allKeyValuesBeforeDelete.size()).isEqualTo(2);

        keyValueStoreService.deleteAllKeyValue();

        List<KeyValueDTO> allKeyValuesAfterDelete = keyValueStoreService.getAllKeyValue();
        assertThat(allKeyValuesAfterDelete).isNotNull();
        assertThat(allKeyValuesAfterDelete.size()).isEqualTo(0);
    }
}