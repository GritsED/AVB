package org.example.users.client;

import org.example.dto.CompanyShortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "companies", url = "${companies.url}")
public interface CompaniesClient {
    @GetMapping("/companies/internal")
    List<CompanyShortDto> getCompaniesByIdsShort(@RequestParam("companyIds") List<Long> companyIds);

    @GetMapping("/companies/internal/{companyId}")
    CompanyShortDto getCompanyShort(@PathVariable("companyId") Long companyId);
}
