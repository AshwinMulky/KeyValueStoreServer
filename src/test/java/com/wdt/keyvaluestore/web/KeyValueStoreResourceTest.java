package com.wdt.keyvaluestore.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdt.keyvaluestore.service.KeyValueStoreService;
import com.wdt.keyvaluestore.service.dto.KeyValueDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(KeyValueStoreResource.class)
public class KeyValueStoreResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeyValueStoreService keyValueStoreService;

    @Test
    public void getKeyValue() throws Exception {
        when(keyValueStoreService.getKeyValue(anyString())).thenReturn(new KeyValueDTO("1", "ABC"));
        this.mockMvc.perform(get("/api/key-values/keys/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("1"))
                .andExpect(jsonPath("$.value").value("ABC"));

        this.mockMvc.perform(get("/api/key-values/keys"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllKeyValues() throws Exception {
        List<KeyValueDTO> keyValueDTOS = new ArrayList<>();
        keyValueDTOS.add(new KeyValueDTO("1", "ABC"));
        keyValueDTOS.add(new KeyValueDTO("2", "DEF"));
        when(keyValueStoreService.getAllKeyValue()).thenReturn(keyValueDTOS);

        this.mockMvc.perform(get("/api/key-values"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[1].key", is("2")))
                .andExpect(jsonPath("$[0].value", is("ABC")))
                .andExpect(jsonPath("$[1].value", is("DEF")));
    }

    @Test
    public void getAllValues() throws Exception {
        List<String> allValues = Arrays.asList("ABC","DEF");
        when(keyValueStoreService.getAllValues()).thenReturn(allValues);

        this.mockMvc.perform(get("/api/key-values/values"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("ABC")))
                .andExpect(jsonPath("$[1]", is("DEF")));
    }

    @Test
    public void createKeyValue() throws Exception {
        when(keyValueStoreService.createKeyValue(any())).thenReturn(new KeyValueDTO("2", "DEF"));

        this.mockMvc.perform(post("/api/key-values")
        .content(convertObjectToJsonBytes(new KeyValueDTO("2", "DEF")))
        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("2"))
                .andExpect(jsonPath("$.value").value("DEF"));

        this.mockMvc.perform(post("/api/key-values")
                .content(convertObjectToJsonBytes(new KeyValueDTO("", "")))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAllKeyValue() throws Exception {
        List<KeyValueDTO> keyValueDTOS = new ArrayList<>();
        keyValueDTOS.add(new KeyValueDTO("1", "ABC"));
        keyValueDTOS.add(new KeyValueDTO("2", "DEF"));
        when(keyValueStoreService.createAllKeyValue(any())).thenReturn(keyValueDTOS);

        mockMvc.perform(post("/api/key-values/all")
                .content(convertObjectToJsonBytes(keyValueDTOS))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("1")))
                .andExpect(jsonPath("$[1].key", is("2")))
                .andExpect(jsonPath("$[0].value", is("ABC")))
                .andExpect(jsonPath("$[1].value", is("DEF")));

        mockMvc.perform(post("/api/key-values/all")
                .content(convertObjectToJsonBytes(new ArrayList<>()))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateKeyValue() throws Exception {
        when(keyValueStoreService.updateKeyValue(any())).thenReturn("PreviousValue");

        this.mockMvc.perform(put("/api/key-values")
                .content(convertObjectToJsonBytes(new KeyValueDTO("2", "DEF")))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("PreviousValue")));

        this.mockMvc.perform(post("/api/key-values")
                .content(convertObjectToJsonBytes(new KeyValueDTO("", "")))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteKeyValue() throws Exception {
        when(keyValueStoreService.deleteKeyValue(anyString())).thenReturn(new KeyValueDTO("1", "ABC"));

        this.mockMvc.perform(delete("/api/key-values/keys/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("1"))
                .andExpect(jsonPath("$.value").value("ABC"));

        this.mockMvc.perform(delete("/api/key-values/keys"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAllKeyValue() throws Exception {
        this.mockMvc.perform(delete("/api/key-values"))
                .andExpect(status().isOk());
    }

    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}