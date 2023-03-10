package com.example.socksstoragerest.service;


import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.entity.SocksEntity;

import java.util.List;

public interface SocksStorageService {
    int getQuantityOfSocksBy(String color, OperationEnum operation, Integer cottonPart);

    void addSocks(SocksEntity socks);

    void removeSocks(SocksEntity socks);

    List<SocksEntity> getInfo();
}
