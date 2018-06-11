package com.wdt.keyvaluestore.service;

import com.wdt.keyvaluestore.KeyValueStoreApplication;
import com.wdt.keyvaluestore.domain.KeyValueStoreAudit;
import com.wdt.keyvaluestore.domain.enumeration.Operation;
import com.wdt.keyvaluestore.repository.KeyValueStoreAuditRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KeyValueStoreApplication.class)
public class KeyValueStoreAuditServiceTest {

    @Autowired
    private KeyValueStoreAuditRepository keyValueStoreAuditRepository;

    @Autowired
    private  KeyValueStoreAuditService keyValueStoreAuditService;

    @Before
    public void setUp() {
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.CREATE, "1", "ABC"));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.CREATE, "2", "DEF"));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.READ, "1", "ABC"));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.READ_ALL, "", ""));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.UPDATE, "1", "ABC"));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.DELETE, "1", "ABC"));
        keyValueStoreAuditRepository.save(new KeyValueStoreAudit(Operation.DELETE_ALL, "", ""));
    }

    @After
    public void tearDown() {
        keyValueStoreAuditRepository.deleteAll();
    }

    @Test
    public void put() {
        List<KeyValueStoreAudit> allRecordsBefforeInsert = keyValueStoreAuditService.getAllRecords();
        assertThat(allRecordsBefforeInsert).isNotNull();
        assertThat(allRecordsBefforeInsert.size()).isEqualTo(7);

        keyValueStoreAuditService.put("1","ABC", Operation.DELETE);

        List<KeyValueStoreAudit> allRecordsAfterInsert = keyValueStoreAuditService.getAllRecords();
        assertThat(allRecordsAfterInsert).isNotNull();
        assertThat(allRecordsAfterInsert.size()).isEqualTo(8);
    }

    @Test
    public void getAllRecords() {
        List<KeyValueStoreAudit> allRecords = keyValueStoreAuditService.getAllRecords();
        assertThat(allRecords).isNotNull();
        assertThat(allRecords.size()).isEqualTo(7);
        assertThat(allRecords.get(0).getOperation()).isEqualByComparingTo(Operation.CREATE);
        assertThat(allRecords.get(0).getKey()).isEqualTo("1");
        assertThat(allRecords.get(0).getValue()).isEqualTo("ABC");
        assertThat(allRecords.get(1).getOperation()).isEqualByComparingTo(Operation.CREATE);
        assertThat(allRecords.get(1).getKey()).isEqualTo("2");
        assertThat(allRecords.get(1).getValue()).isEqualTo("DEF");
        assertThat(allRecords.get(2).getOperation()).isEqualByComparingTo(Operation.READ);
        assertThat(allRecords.get(2).getKey()).isEqualTo("1");
        assertThat(allRecords.get(2).getValue()).isEqualTo("ABC");
        assertThat(allRecords.get(3).getOperation()).isEqualByComparingTo(Operation.READ_ALL);
        assertThat(allRecords.get(3).getKey()).isEqualTo("");
        assertThat(allRecords.get(3).getValue()).isEqualTo("");
        assertThat(allRecords.get(4).getOperation()).isEqualByComparingTo(Operation.UPDATE);
        assertThat(allRecords.get(4).getKey()).isEqualTo("1");
        assertThat(allRecords.get(4).getValue()).isEqualTo("ABC");
        assertThat(allRecords.get(5).getOperation()).isEqualByComparingTo(Operation.DELETE);
        assertThat(allRecords.get(5).getKey()).isEqualTo("1");
        assertThat(allRecords.get(5).getValue()).isEqualTo("ABC");
        assertThat(allRecords.get(6).getOperation()).isEqualByComparingTo(Operation.DELETE_ALL);
        assertThat(allRecords.get(6).getKey()).isEqualTo("");
        assertThat(allRecords.get(6).getValue()).isEqualTo("");
    }

    @Test
    public void getAllRecordsForOperation() {
        List<KeyValueStoreAudit> allCreateRecords = keyValueStoreAuditService.getAllRecords(Operation.CREATE);
        assertThat(allCreateRecords).isNotNull();
        assertThat(allCreateRecords.size()).isEqualTo(2);
        assertThat(allCreateRecords.get(0).getOperation()).isEqualByComparingTo(Operation.CREATE);
        assertThat(allCreateRecords.get(0).getKey()).isEqualTo("1");
        assertThat(allCreateRecords.get(0).getValue()).isEqualTo("ABC");
        assertThat(allCreateRecords.get(1).getOperation()).isEqualByComparingTo(Operation.CREATE);
        assertThat(allCreateRecords.get(1).getKey()).isEqualTo("2");
        assertThat(allCreateRecords.get(1).getValue()).isEqualTo("DEF");

        List<KeyValueStoreAudit> allReadRecords = keyValueStoreAuditService.getAllRecords(Operation.READ);
        assertThat(allReadRecords).isNotNull();
        assertThat(allReadRecords.size()).isEqualTo(1);
        assertThat(allReadRecords.get(0).getOperation()).isEqualByComparingTo(Operation.READ);
        assertThat(allReadRecords.get(0).getKey()).isEqualTo("1");
        assertThat(allReadRecords.get(0).getValue()).isEqualTo("ABC");

        List<KeyValueStoreAudit> allReadAllRecords = keyValueStoreAuditService.getAllRecords(Operation.READ_ALL);
        assertThat(allReadAllRecords).isNotNull();
        assertThat(allReadAllRecords.size()).isEqualTo(1);
        assertThat(allReadAllRecords.get(0).getOperation()).isEqualByComparingTo(Operation.READ_ALL);
        assertThat(allReadAllRecords.get(0).getKey()).isEqualTo("");
        assertThat(allReadAllRecords.get(0).getValue()).isEqualTo("");

        List<KeyValueStoreAudit> allUpdateRecords = keyValueStoreAuditService.getAllRecords(Operation.UPDATE);
        assertThat(allUpdateRecords).isNotNull();
        assertThat(allUpdateRecords.size()).isEqualTo(1);
        assertThat(allUpdateRecords.get(0).getOperation()).isEqualByComparingTo(Operation.UPDATE);
        assertThat(allUpdateRecords.get(0).getKey()).isEqualTo("1");
        assertThat(allUpdateRecords.get(0).getValue()).isEqualTo("ABC");

        List<KeyValueStoreAudit> allDeleteRecords = keyValueStoreAuditService.getAllRecords(Operation.DELETE);
        assertThat(allDeleteRecords).isNotNull();
        assertThat(allDeleteRecords.size()).isEqualTo(1);
        assertThat(allDeleteRecords.get(0).getOperation()).isEqualByComparingTo(Operation.DELETE);
        assertThat(allDeleteRecords.get(0).getKey()).isEqualTo("1");
        assertThat(allDeleteRecords.get(0).getValue()).isEqualTo("ABC");

        List<KeyValueStoreAudit> allDeleteAllRecords = keyValueStoreAuditService.getAllRecords(Operation.DELETE_ALL);
        assertThat(allDeleteAllRecords).isNotNull();
        assertThat(allDeleteAllRecords.size()).isEqualTo(1);
        assertThat(allDeleteAllRecords.get(0).getOperation()).isEqualByComparingTo(Operation.DELETE_ALL);
        assertThat(allDeleteAllRecords.get(0).getKey()).isEqualTo("");
        assertThat(allDeleteAllRecords.get(0).getValue()).isEqualTo("");
    }

}