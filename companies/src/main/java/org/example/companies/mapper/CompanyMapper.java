package org.example.companies.mapper;

import org.example.companies.dto.CompanyFullDto;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.model.Company;
import org.example.dto.CompanyShortDto;
import org.example.dto.UserShortDto;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "company.id", target = "id")
    @Mapping(source = "company.name", target = "name")
    @Mapping(source = "company.budget", target = "budget")
    @Mapping(source = "users", target = "users")
    CompanyFullDto toDto(Company company, List<UserShortDto> users);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "budget", target = "budget")
    CompanyShortDto toShortDto(Company company);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "budget", target = "budget")
    Company toEntity(NewCompanyDto newCompanyDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompany(@MappingTarget Company company, UpdateCompanyDto updateCompanyDto);

    default List<CompanyFullDto> toDtoList(List<Company> companies, Map<Long, List<UserShortDto>> usersMap) {
        return companies.stream()
                .map(company -> toDto(company, usersMap.getOrDefault(company.getId(), Collections.emptyList())))
                .toList();
    }

    List<CompanyShortDto> toShortDtoList(List<Company> companies);
}
