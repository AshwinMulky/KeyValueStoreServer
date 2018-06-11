package com.wdt.keyvaluestore.web;

import com.wdt.keyvaluestore.service.KeyValueStoreService;
import com.wdt.keyvaluestore.service.dto.KeyValueDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/key-values")
@CrossOrigin(origins = {"http://localhost:4200"}) //Allow our Angular application to access the apis
public class KeyValueStoreResource {

    @Inject
    private KeyValueStoreService keyValueStoreService;

    /*
     * get(key)
     */
    @GetMapping("/keys/{key}")
    public ResponseEntity<?> getKeyValue(@PathVariable String key) {
        if(StringUtils.isEmpty(key))
            return new ResponseEntity<>("Key cannot be empty",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(keyValueStoreService.getKeyValue(key), HttpStatus.OK);
    }

    /*
     * getAll() key-value pairs
     */
    @GetMapping("")
    public ResponseEntity<List<KeyValueDTO>> getAllKeyValues() {
        return new ResponseEntity<>(keyValueStoreService.getAllKeyValue(), HttpStatus.OK);
    }

    /*
     * getAll() only values
     */
    @GetMapping("/values")
    public ResponseEntity<List<String>> getAllValues() {
        return new ResponseEntity<>(keyValueStoreService.getAllValues(), HttpStatus.OK);
    }

    /*
     * put(key,value)
     */
    @PostMapping("")
    public ResponseEntity<?> createKeyValue(@Valid @RequestBody KeyValueDTO keyValueDTO) {
        if(keyValueDTO != null && (StringUtils.isEmpty(keyValueDTO.getKey()) || StringUtils.isEmpty(keyValueDTO.getValue()))){
            return new ResponseEntity<>("Key/Value cannot be empty",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(keyValueStoreService.createKeyValue(keyValueDTO), HttpStatus.OK);
    }

    /*
     * putAll()
     */
    @PostMapping("/all")
    public ResponseEntity<?> createAllKeyValue(@Valid @RequestBody List<KeyValueDTO> keyValues) {
        if(keyValues.size()< 1)
            return new ResponseEntity<>("Key/Value cannot be empty",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(keyValueStoreService.createAllKeyValue(keyValues), HttpStatus.OK);
    }

    /*
     * put(key,value) - update
     */
    @PutMapping("")
    public ResponseEntity<String> updateKeyValue(@Valid @RequestBody KeyValueDTO keyValueDTO) {
        if(keyValueDTO != null && (StringUtils.isEmpty(keyValueDTO.getKey()) || StringUtils.isEmpty(keyValueDTO.getValue()))){
            return new ResponseEntity<>("Key/Value cannot be empty",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(keyValueStoreService.updateKeyValue(keyValueDTO), HttpStatus.OK);
    }

    /*
     * delete(key)
     */
    @DeleteMapping("/keys/{key}")
    public ResponseEntity<?> deleteKeyValue(@PathVariable String key) {
        if(StringUtils.isEmpty(key))
            return new ResponseEntity<>("Key cannot be empty",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(keyValueStoreService.deleteKeyValue(key), HttpStatus.OK);
    }

    /*
     * delete(key)
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllKeyValue() {
        keyValueStoreService.deleteAllKeyValue();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
