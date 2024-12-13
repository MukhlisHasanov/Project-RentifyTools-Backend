package org.rentifytools.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.addressDto.AddressResponseDto;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Getting all addresses from DB")
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAddresses() {
        List<AddressResponseDto> tools = addressService.getAddresses();
        if (tools.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    @Operation(summary = "Adding new address to the DB")
    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto dto) {
        return new ResponseEntity<>(addressService.addAddress(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Editing user information")
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable(name = "addressId") Long addressId, @RequestBody AddressRequestDto addressDto) {
        return new ResponseEntity<>(addressService.updateAddress(addressId, addressDto), HttpStatus.OK);
    }
}
