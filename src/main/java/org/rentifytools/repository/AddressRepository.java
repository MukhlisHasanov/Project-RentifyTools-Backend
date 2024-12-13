package org.rentifytools.repository;

import org.rentifytools.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    void deleteById(Long id);

    List<Address> id(Long id);
}
