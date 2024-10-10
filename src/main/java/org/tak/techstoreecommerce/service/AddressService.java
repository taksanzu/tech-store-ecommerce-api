package org.tak.techstoreecommerce.service;

import jakarta.validation.Valid;
import org.tak.techstoreecommerce.dto.AddressDTO;
import org.tak.techstoreecommerce.model.User;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddress();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser(String email);

    AddressDTO addAddress(@Valid AddressDTO addressDTO, User user);

    AddressDTO updateAddress(Long addressId, @Valid AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}
