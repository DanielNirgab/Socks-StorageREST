package com.example.socksstoragerest.controller;


import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.dto.SocksDto;
import com.example.socksstoragerest.entity.SocksEntity;
import com.example.socksstoragerest.service.SocksStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/socks")
public class SocksStorageController {
    private final Logger logger = LoggerFactory.getLogger(SocksStorageController.class);
    private final SocksStorageService socksService;

    @Operation(summary = "addSocksToStock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock was refiled successfully.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Missing or incompatible params.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server side error occurs.", content = @Content)
            })
    @PostMapping(path = "/income")
    public ResponseEntity<SocksDto> addSocksToStock(@Valid @RequestBody SocksDto socksDto) {
        logger.info("Was invoked 'addSocksToStock' method from {}", SocksStorageController.class.getSimpleName());
        socksService.addSocks(socksDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getQuantityOfSocksBy(params...)",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "400", description = "Missing or incompatible params.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server side error occurs.", content = @Content)
            })
    @GetMapping
    public ResponseEntity<Integer> getQuantityOfSocksBy(@RequestParam String color,
                                                        @RequestParam OperationEnum operation,
                                                        @RequestParam Integer cottonPart) {
        logger.info("Was invoked 'getQualityOfSocksBy' method from {}", SocksStorageController.class.getSimpleName());
        return ResponseEntity.ok(socksService.getQuantityOfSocksBy(color, operation, cottonPart));
    }

    @Operation(summary = "removeSocksFromStorage",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Socks was removed successfully.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Missing or incompatible params.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server side error occurs.", content = @Content)
            })
    @PostMapping(path = "/outcome")
    public ResponseEntity<SocksDto> takeSocksFromStorage(@Valid @RequestBody SocksDto socksDto) {
        logger.info("Was invoked 'takeSocksFromStorage' method from {}", SocksStorageController.class.getSimpleName());
        socksService.removeSocks(socksDto);
        return ResponseEntity.ok().build();
    }
}
