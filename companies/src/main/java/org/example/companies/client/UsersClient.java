package org.example.companies.client;

import org.example.dto.UserShortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "users", url = "${users.url}")
public interface UsersClient {
    @GetMapping("/users/internal")
    List<UserShortDto> getUsersByCompanyIds(@RequestParam("companyIds") List<Long> companyIds,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping("/users/internal/{companyId}")
    List<UserShortDto> getUsersByCompanyId(@PathVariable("companyId") Long companyId,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size);
}
