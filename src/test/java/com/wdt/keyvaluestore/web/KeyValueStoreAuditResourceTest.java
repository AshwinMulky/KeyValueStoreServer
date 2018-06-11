package com.wdt.keyvaluestore.web;

import com.wdt.keyvaluestore.domain.KeyValueStoreAudit;
import com.wdt.keyvaluestore.domain.enumeration.Operation;
import com.wdt.keyvaluestore.service.KeyValueStoreAuditService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KeyValueStoreAuditResource.class)
public class KeyValueStoreAuditResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeyValueStoreAuditService keyValueStoreAuditService;

    @Test
    public void getAllAuditRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.CREATE, "1", "ABC"));
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.READ, "1", "ABC"));
        when(keyValueStoreAuditService.getAllRecords()).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[1].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("ABC")))
                .andExpect(jsonPath("$[1].value", is("ABC")))
                .andExpect(jsonPath("$[0].operation", is("CREATE")))
                .andExpect(jsonPath("$[1].operation", is("READ")));
    }

    @Test
    public void getAllCreateRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.CREATE, "1", "ABC"));
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.CREATE, "2", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.CREATE)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[1].key", is("2")))
                .andExpect(jsonPath("$[0].value", is("ABC")))
                .andExpect(jsonPath("$[1].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("CREATE")))
                .andExpect(jsonPath("$[1].operation", is("CREATE")));
    }

    @Test
    public void getAllUpdateRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.UPDATE, "1", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.UPDATE)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("UPDATE")));
    }

    @Test
    public void getAllDelteRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.DELETE, "1", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.DELETE)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("DELETE")));
    }

    @Test
    public void getAllDeleteAllRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.DELETE_ALL, "1", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.DELETE_ALL)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/delete-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("DELETE_ALL")));
    }

    @Test
    public void getAllReadRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.READ, "1", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.READ)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("READ")));
    }

    @Test
    public void getAllReadAllRecords() throws Exception {
        List<KeyValueStoreAudit> keyValueStoreAudits = new ArrayList<>();
        keyValueStoreAudits.add(new KeyValueStoreAudit(Operation.READ_ALL, "1", "DEF"));
        when(keyValueStoreAuditService.getAllRecords(Operation.READ_ALL)).thenReturn(keyValueStoreAudits);

        this.mockMvc.perform(get("/api/audits/read-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[0].value", is("DEF")))
                .andExpect(jsonPath("$[0].operation", is("READ_ALL")));
    }
}