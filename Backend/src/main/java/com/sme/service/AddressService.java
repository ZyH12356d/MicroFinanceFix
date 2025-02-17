package com.sme.service;

import com.sme.entity.Address;
import com.sme.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Get all addresses
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Get an address by ID
    public Optional<Address> getAddressById(long id) {
        return addressRepository.findById(id);
    }

    // Create a new address
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    // Update an existing address
    public Address updateAddress(long id, Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(id);

        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setCity(addressDetails.getCity());
            address.setTown(addressDetails.getTown());
            address.setStreet(addressDetails.getStreet());
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found with id: " + id);
        }
    }

    // Delete an address by ID
    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }
}