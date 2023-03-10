package com.example.socksstoragerest.service.impl;

import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.entity.SocksEntity;
import com.example.socksstoragerest.exception.SocksPairNotFound;
import com.example.socksstoragerest.repository.SocksRepository;
import com.example.socksstoragerest.service.SocksStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocksStorageImpl implements SocksStorageService {
    private final SocksRepository socksRepository;

    /**
     * Get number of socks available in stock based on color,
     * cotton part and comparison operators.
     * @param color name of color
     * @param operation comparison operator from {@link OperationEnum}
     * @param cottonPart desired amount of cotton
     * @return total number of socks matching the desired params OR zero if nothing matches
     */

    @Override
    public int getQuantityOfSocksBy(String color, OperationEnum operation, Integer cottonPart) {
        Collection<SocksEntity> foundSocks = new ArrayList<>();
        switch (operation) {
            case equal -> {
                Optional<SocksEntity> socksStock = socksRepository.findByColorAndCottonPart(color, cottonPart);
                return socksStock.isPresent() ? socksStock.get().getQuantity() : 0;
            }
            case moreThan -> foundSocks = socksRepository.findByColorAndCottonPartAfter(color, cottonPart);
            case lessThan -> foundSocks = socksRepository.findByColorAndCottonPartBefore(color, cottonPart);
        }
        return foundSocks.stream().map(SocksEntity::getQuantity).reduce(0, Integer::sum);
    }

    /**
     * Add socks to database.
     * If DB contains entry with the same color and cotton part, then its amount will be increased by quantity.
     * @param socks {@link SocksEntity} instance from user's request.
     */
    @Override
    public void addSocks(SocksEntity socks) {
        Optional<SocksEntity> stockOptional = socksRepository
                .findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        SocksEntity newSocksStock;
        if (stockOptional.isPresent()) {
            newSocksStock = stockOptional.get();
            newSocksStock.setQuantity(newSocksStock.getQuantity() + socks.getQuantity());
        } else {
            newSocksStock = socks;
        }
        socksRepository.save(newSocksStock);
    }

    /**
     * Remove desired quantity of socks from DB.
     * @param socks {@link SocksEntity} instance from user's request.
     * @throws SocksPairNotFound if socks not present
     */
    @Override
    public void removeSocks(SocksEntity socks) throws SocksPairNotFound {
        SocksEntity socksStock = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart())
                .orElseThrow();
        if (socksStock.getQuantity() < socks.getQuantity()) {
            throw new SocksPairNotFound();
        }
        socksStock.setQuantity(socksStock.getQuantity() - socks.getQuantity());
        socksRepository.save(socksStock);
    }
    @Override
    public List<SocksEntity> getInfo() {
        return socksRepository.findAll();
    }
}
