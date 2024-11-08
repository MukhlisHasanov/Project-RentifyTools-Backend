package org.rentifytools.service;

import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.addressDto.AddressDto;
import org.rentifytools.entity.Address;
import org.rentifytools.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address createAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setStrasse(addressDto.getStrasse());
        address.setStadt(addressDto.getStadt());
        address.setPlz(addressDto.getPlz());
        address.setLand(addressDto.getLand());
        address.setTelefonnummer(addressDto.getTelefonnummer());

        return addressRepository.save(address);
    }
}
