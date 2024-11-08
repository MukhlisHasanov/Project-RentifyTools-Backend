package org.rentifytools.controller;

import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.addressDto.AddressDto;
import org.rentifytools.entity.Address;
import org.rentifytools.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto) {
        Address address = addressService.createAddress(addressDto);
        return ResponseEntity.ok(address);
    }
}
