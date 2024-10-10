package org.tak.techstoreecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.techstoreecommerce.dto.AddressDTO;
import org.tak.techstoreecommerce.exception.APIException;
import org.tak.techstoreecommerce.exception.ResourceNotFoundException;
import org.tak.techstoreecommerce.model.Address;
import org.tak.techstoreecommerce.model.User;
import org.tak.techstoreecommerce.repository.AddressRepository;
import org.tak.techstoreecommerce.repository.UserRepository;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new APIException("No address found");
        }
        List<AddressDTO> addressDTOS = addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();

        return addressDTOS;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new APIException("Address not found"));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByUser(String email) {
        List<Address> addresses = addressRepository.findAddressByUser(email);
        if (addresses.isEmpty()) {
            throw new APIException("No address found");
        }
        List<AddressDTO> addressDTOS = addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();

        return addressDTOS;
    }

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addresses = user.getAddresses();
        addresses.add(address);
        user.setAddresses(addresses);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());

        Address updatedAddress = addressRepository.save(address);

        User user = updatedAddress.getUser();
        List<Address> addresses = user.getAddresses();
        addresses.removeIf(a -> a.getId().equals(addressId));
        addresses.add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
        User user = address.getUser();
        List<Address> addresses = user.getAddresses();
        addresses.removeIf(a -> a.getId().equals(addressId));
        userRepository.save(user);
        addressRepository.delete(address);

        return "Address deleted successfully";
    }


}
