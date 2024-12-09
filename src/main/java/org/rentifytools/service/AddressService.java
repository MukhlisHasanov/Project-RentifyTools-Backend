package org.rentifytools.service;

import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.addressDto.AddressResponseDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto addAddress(AddressRequestDto address);

    AddressResponseDto updateAddress(AddressRequestDto address);

    AddressResponseDto deleteAddress(Long id);

    List<AddressResponseDto> getAddresses();
}
