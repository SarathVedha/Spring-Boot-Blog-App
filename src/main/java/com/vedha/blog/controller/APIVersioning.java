package com.vedha.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/versioning")
@Tag(name = "APIs Versioning", description = "Types Of APIs Versioning")
public class APIVersioning {

    /**
     * Versioning API by URI Level By Adding V1,V2,... In URI
     */
    // http://localhost:8080/api/versioning/v1/uri
    @GetMapping(value = "/v1/uri")
    @Operation(summary = "URI Version v1", description = "API URI Versioning v1")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> uriVersioningV1() {

        return ResponseEntity.ok("API URI Versioning v1");
    }

    // http://localhost:8080/api/versioning/v1/uri
    @GetMapping(value = "/v2/uri")
    @Operation(summary = "URI Version v2", description = "API URI Versioning v2")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> uriVersioningV2() {

        return ResponseEntity.ok("API URI Versioning v2");
    }


    /**
     * Versioning API by QueryParam Level By Adding version=1,version=2,... In QueryParam
     */
    // http://localhost:8080/api/versioning/queryparam?version=1
    @GetMapping(value = "/queryParam", params = "version=1")
    @Operation(summary = "QueryParam Version=1", description = "API QueryParam Versioning version=1")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> queryParamVersioningV1(@RequestParam("version") Long version) {

        return ResponseEntity.ok("API QueryParam Versioning v".concat(version.toString()));
    }

    // http://localhost:8080/api/versioning/queryparam?version=2
    @GetMapping(value = "/queryParam", params = "version=2")
    @Operation(summary = "QueryParam Version=2", description = "API QueryParam Versioning version=2")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> queryParamVersioningV2(@RequestParam("version") Long version) {

        return ResponseEntity.ok("API QueryParam Versioning v".concat(version.toString()));
    }


    /**
     * Versioning API by Header Level By Adding X-APP-VERSION=1,X-APP-VERSION=2,... In Header
     * Add In Header X-APP-VERSION=1
     */
    // http://localhost:8080/api/versioning/header
    @GetMapping(value = "/header", headers = "X-APP-VERSION=1")
    @Operation(summary = "Header X-APP-VERSION=1", description = "API Header Versioning X-APP-VERSION=1")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> headerVersioningV1() {

        return ResponseEntity.ok("API Header Versioning v1");
    }

    // http://localhost:8080/api/versioning/header
    @GetMapping(value = "/header", headers = "X-APP-VERSION=2")
    @Operation(summary = "Header X-APP-VERSION=2", description = "API Header Versioning X-APP-VERSION=2")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    public ResponseEntity<String> headerVersioningV2() {

        return ResponseEntity.ok("API Header Versioning v2");
    }

}
