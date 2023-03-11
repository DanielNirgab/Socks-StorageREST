package com.example.socksstoragerest.mapper;


import com.example.socksstoragerest.dto.SocksDto;
import com.example.socksstoragerest.entity.SocksEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SocksMapper {
    @Mapping(target = "color", source = "color")
    @Mapping(target = "cottonPart", source = "cottonPart")
    SocksDto toDto(SocksEntity socks);
    @Mapping(target = "color", source = "color")
    @Mapping(target = "cottonPart", source = "cottonPart")
    SocksEntity toEntity(SocksDto socksStockDto);
}
