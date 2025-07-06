package org.example.companies.client;

import org.example.dto.UserShortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "users")
public interface UsersClient {
    @GetMapping("/users/internal")
    List<UserShortDto> getUsersByCompanyIds(@RequestParam("companyIds") List<Long> companyIds);

    @GetMapping("/users/internal/{companyId}")
    List<UserShortDto> getUsersByCompanyId(@PathVariable("companyId") Long companyId);
}
