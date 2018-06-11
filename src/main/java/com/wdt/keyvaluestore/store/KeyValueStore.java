package com.wdt.keyvaluestore.store;

import com.wdt.keyvaluestore.domain.enumeration.Operation;
import com.wdt.keyvaluestore.service.KeyValueStoreAuditService;
import com.wdt.keyvaluestore.service.dto.KeyValueDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Scope(value = "singleton")
@Component
public class KeyValueStore {

    @Inject
    private KeyValueStoreAuditService keyValueStoreAuditService;

    private Map<String, String> keyValues;

    private static final String EMPTY = "";

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    public KeyValueStore() {
        this.keyValues = new HashMap<>();
    }

    public void create(String key, String value) {
        keyValueStoreAuditService.put(key, value, Operation.CREATE);
        writeLock.lock();
        try {
            this.keyValues.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public void createAll(List<KeyValueDTO> keyValueDTOs) {
        writeLock.lock(); //let this thread complete its job
        try {
            keyValueDTOs.forEach(keyValueDTO -> {
                // this.put(keyValueDTO.getKey(), keyValueDTO.getValue()); //don't lock for each entry
                keyValueStoreAuditService.put(keyValueDTO.getKey(), keyValueDTO.getValue(), Operation.CREATE);
                this.keyValues.put(keyValueDTO.getKey(), keyValueDTO.getValue());
            });
        } finally {
            writeLock.unlock();
        }
    }

    public String update(String key, String value) {
        keyValueStoreAuditService.put(key, value, Operation.UPDATE);
        String previousValue;
        writeLock.lock();
        try {
            previousValue = this.keyValues.put(key, value);
        } finally {
            writeLock.unlock();
        }
        return previousValue;
    }

    public String get(String key) {
        String value;
        readLock.lock();
        try {
            value = this.keyValues.get(key);
        } finally {
            readLock.unlock();
        }
        keyValueStoreAuditService.put(key, value, Operation.READ);
        return value;
    }

    public List<KeyValueDTO> getAllKeyValue() {
        keyValueStoreAuditService.put(EMPTY, EMPTY, Operation.READ_ALL);
        List<KeyValueDTO> keyValueDTOS;
        readLock.lock();
        try {
            keyValueDTOS = this.keyValues.entrySet()
                    .stream()
                    .map(e -> new KeyValueDTO(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
        return keyValueDTOS;
    }

    public List<String> getAllValues() {
        keyValueStoreAuditService.put(EMPTY, EMPTY, Operation.READ_ALL);
        List<String> allValues;
        readLock.lock();
        try {
            allValues = new ArrayList<>(this.keyValues.values());
        } finally {
            readLock.unlock();
        }
        return allValues;
    }

    public String delete(String key) {
        String previousValue;
        writeLock.lock();
        try {
            previousValue = this.keyValues.remove(key);
        } finally {
            writeLock.unlock();
        }
        keyValueStoreAuditService.put(key, previousValue, Operation.DELETE);
        return previousValue;
    }

    public void deleteAll() {
        keyValueStoreAuditService.put(EMPTY, EMPTY, Operation.DELETE_ALL);
        writeLock.lock();
        try {
            this.keyValues.clear();
        } finally {
            writeLock.unlock();
        }
    }
}
