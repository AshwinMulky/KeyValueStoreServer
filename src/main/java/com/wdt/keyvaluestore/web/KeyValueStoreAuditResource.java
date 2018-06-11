package com.wdt.keyvaluestore.web;

import com.wdt.keyvaluestore.domain.KeyValueStoreAudit;
import com.wdt.keyvaluestore.domain.enumeration.Operation;
import com.wdt.keyvaluestore.service.KeyValueStoreAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/audits")
@CrossOrigin(origins = {"http://localhost:4200"}) //Allow our Angular application to access the apis
public class KeyValueStoreAuditResource {

    @Inject
    private KeyValueStoreAuditService keyValueStoreAuditService;

    /*
     * Get All Audit Records
     */
    @GetMapping("")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllAuditRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(), HttpStatus.OK);
    }

    /*
     * Get All Create Records
     */
    @GetMapping("/create")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllCreateRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.CREATE), HttpStatus.OK);
    }

    /*
     * Get All Update Records
     */
    @GetMapping("/update")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllUpdateRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.UPDATE), HttpStatus.OK);
    }

    /*
     * Get All Delete Records
     */
    @GetMapping("/delete")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllDelteRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.DELETE), HttpStatus.OK);
    }

    /*
     * Get All Delete_all Records
     */
    @GetMapping("/delete-all")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllDeleteAllRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.DELETE_ALL), HttpStatus.OK);
    }

    /*
     * Get All Read Records
     */
    @GetMapping("/read")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllReadRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.READ), HttpStatus.OK);
    }

    /*
     * Get All Read_all Records
     */
    @GetMapping("/read-all")
    public ResponseEntity<List<KeyValueStoreAudit>> getAllReadAllRecords() {
        return new ResponseEntity<>(keyValueStoreAuditService.getAllRecords(Operation.READ_ALL), HttpStatus.OK);
    }
}
