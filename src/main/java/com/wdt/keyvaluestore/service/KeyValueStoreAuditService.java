package com.wdt.keyvaluestore.service;

import com.wdt.keyvaluestore.domain.KeyValueStoreAudit;
import com.wdt.keyvaluestore.domain.enumeration.Operation;
import com.wdt.keyvaluestore.repository.KeyValueStoreAuditRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class KeyValueStoreAuditService {

    @Inject
    private KeyValueStoreAuditRepository keyValueStoreAuditRepository;

    public void put(String key, String value, Operation operation) {
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(operation, key, value));
    }

    public List<KeyValueStoreAudit> getAllRecords() {
        return keyValueStoreAuditRepository.findAll();
    }

    public List<KeyValueStoreAudit> getAllRecords(Operation operation) {
        return keyValueStoreAuditRepository.findAllByOperation(operation);
    }
}
