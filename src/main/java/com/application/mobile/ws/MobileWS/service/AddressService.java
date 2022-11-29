package com.application.mobile.ws.MobileWS.service;

import com.application.mobile.ws.MobileWS.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);

    AddressDTO getAddress(String addressId);
}
