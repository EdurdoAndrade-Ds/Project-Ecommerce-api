package org.ecommerce.ecommerceapi.modules.version.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/version")
public class VersionController {

    @GetMapping
    public ResponseEntity<String> versionApi() {
        return ResponseEntity.ok().body("Versao da API: 2.0.1");
    }
}
