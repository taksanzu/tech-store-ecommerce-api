package org.tak.techstoreecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tak.techstoreecommerce.dto.AddressDTO;
import org.tak.techstoreecommerce.model.User;
import org.tak.techstoreecommerce.service.AddressService;
import org.tak.techstoreecommerce.util.AuthUtil;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressServiceImpl;

    @Autowired
    private AuthUtil authUtil;


    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddress() {
        List<AddressDTO> addressDTOS = addressServiceImpl.getAllAddress();
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressServiceImpl.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AddressDTO>> getAddressByUser() {
        String email = authUtil.loggedInEmail();
        List<AddressDTO> addressDTOS = addressServiceImpl.getAddressByUser(email);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddress = addressServiceImpl.addAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressServiceImpl.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String status = addressServiceImpl.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
