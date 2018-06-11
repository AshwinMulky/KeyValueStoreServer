package com.wdt.keyvaluestore.service;

import com.wdt.keyvaluestore.service.dto.KeyValueDTO;
import com.wdt.keyvaluestore.store.KeyValueStore;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class KeyValueStoreService {

    @Inject
    private KeyValueStore keyValueStore;

    public KeyValueDTO getKeyValue(String key) {
        return new KeyValueDTO(key, keyValueStore.get(key));
    }

    public List<KeyValueDTO> getAllKeyValue() {
        return keyValueStore.getAllKeyValue();
    }

    public List<String> getAllValues() {
        return keyValueStore.getAllValues();
    }

    public KeyValueDTO createKeyValue(KeyValueDTO keyValueDTO) {
        keyValueStore.create(keyValueDTO.getKey(), keyValueDTO.getValue());
        return keyValueDTO;
    }

    public List<KeyValueDTO> createAllKeyValue(List<KeyValueDTO> keyValues) {
        keyValueStore.createAll(keyValues);
        return keyValues;
    }

    public String updateKeyValue(KeyValueDTO keyValueDTO) {
        return keyValueStore.update(keyValueDTO.getKey(), keyValueDTO.getValue());
    }

    public KeyValueDTO deleteKeyValue(String key) {
        return new KeyValueDTO(key, keyValueStore.delete(key));
    }

    public void deleteAllKeyValue() {
        keyValueStore.deleteAll();
    }
}
