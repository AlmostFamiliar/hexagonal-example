package de.strasser.peter.hexagonal.web;

import de.strasser.peter.hexagonal.application.customer.port.in.AddAddressUseCase;
import de.strasser.peter.hexagonal.application.customer.port.in.commands.AddAddressCommand;
import de.strasser.peter.hexagonal.web.dto.request.AddAddressRequest;
import de.strasser.peter.hexagonal.web.mapper.AddAddressWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddAddressController {
  private final AddAddressUseCase addAddressUseCase;
  private final AddAddressWebMapper addAddressMapper;

  @PostMapping("/v1/customer/address")
  public void addAddress(
      @RequestParam BigInteger customerId, @RequestBody AddAddressRequest addAddressRequest) {
    final List<AddAddressCommand> addAddressCmds =
        List.of(addAddressMapper.toCmd(addAddressRequest));
    addAddressUseCase.addAddresses(customerId, addAddressCmds);
  }
}
