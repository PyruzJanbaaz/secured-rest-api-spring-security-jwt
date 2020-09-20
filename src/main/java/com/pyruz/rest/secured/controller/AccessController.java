package com.pyruz.rest.secured.controller;

import com.pyruz.rest.secured.model.domain.AccessAddRequest;
import com.pyruz.rest.secured.model.domain.AccessUpdateRequest;
import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.service.AccessService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
public class AccessController  {

    final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }


    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @PostMapping(value = "v1/access")
    public ResponseEntity<?> addAccess(@Valid @RequestBody AccessAddRequest accessAddRequest){
        return new ResponseEntity<>(accessService.addAccess(accessAddRequest),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @PutMapping(value = "v1/access")
    public ResponseEntity<?> updateAccess(@Valid @RequestBody AccessUpdateRequest accessUpdateRequest) {
        return new ResponseEntity<>(accessService.editAccess(accessUpdateRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @DeleteMapping(value = "v1/access")
    public ResponseEntity<?> deleteAccess(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        return new ResponseEntity<>(accessService.deleteAccess(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @GetMapping(value = "v1/accesses")
    public ResponseEntity<?> getAccesses() {
        BaseDTO baseDTO=accessService.getAllAccesses();
        return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @GetMapping(value = "v1/accesses/page")
    public ResponseEntity<?> getAccessByFilter(@ApiParam(value = "0", name = "pageNumber", required = true) @RequestParam @Min(0) Integer pageNumber,
                                               @ApiParam(value = "10", name = "pageSize", required = true) @RequestParam @Min(1) Integer pageSize) {
        BaseDTO baseDTO = accessService.getAllAccesses(pageNumber, pageSize);
        return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ACCESS')")
    @GetMapping(value = "v1/access")
    public ResponseEntity<?> getAccess(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        BaseDTO baseDTO = accessService.getAccess(id);
        return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);
    }

}