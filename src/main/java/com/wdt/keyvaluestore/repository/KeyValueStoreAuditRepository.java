package com.wdt.keyvaluestore.repository;

import com.wdt.keyvaluestore.domain.KeyValueStoreAudit;
import com.wdt.keyvaluestore.domain.enumeration.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyValueStoreAuditRepository extends JpaRepository<KeyValueStoreAudit, Long> {
    List<KeyValueStoreAudit> findAllByOperation(Operation operation);
}

