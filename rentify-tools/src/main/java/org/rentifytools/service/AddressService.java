package org.rentifytools.service;

import org.rentifytools.dto.addressDto.AddressDto;
import org.rentifytools.entity.Address;

public interface AddressService {
    Address createAddress(AddressDto addressDto);
}
