package com.wdt.keyvaluestore.domain;

import com.wdt.keyvaluestore.domain.enumeration.Operation;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * An Audit Entry.
 */
@Entity
@Table(name = "key_value_store_audit")
public class KeyValueStoreAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation")
    private Operation operation;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate = OffsetDateTime.now(ZoneOffset.UTC);

    public KeyValueStoreAudit() {} //to satisfy jpa

    public KeyValueStoreAudit(Operation operation, String key, String value) {
        this.operation = operation;
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "KeyValueStoreAudit{" +
                "id=" + id +
                ", operation=" + operation +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
