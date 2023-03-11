package com.example.socksstoragerest.service;


import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.dto.SocksDto;
import com.example.socksstoragerest.exception.SocksPairNotFound;
import com.example.socksstoragerest.exception.WrongColorException;


public interface SocksStorageService {
    int getQuantityOfSocks(String color, OperationEnum operation, Integer cottonPart);

    void addSocks(SocksDto socksDto) throws WrongColorException;

    void removeSocks(SocksDto socksDto) throws SocksPairNotFound;

}
