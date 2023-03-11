package com.example.socksstoragerest.service.impl;

import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.dto.SocksDto;
import com.example.socksstoragerest.entity.SocksEntity;
import com.example.socksstoragerest.exception.SocksPairNotFound;
import com.example.socksstoragerest.exception.WrongColorException;
import com.example.socksstoragerest.mapper.SocksMapper;
import com.example.socksstoragerest.repository.SocksRepository;
import com.example.socksstoragerest.service.SocksStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocksStorageImpl implements SocksStorageService {
    private final Logger logger = LoggerFactory.getLogger(SocksStorageImpl.class);
    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

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
        logger.info("Was invoked 'getQuantityOfSocksBy' method from {}", SocksStorageImpl.class.getSimpleName());
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
     * @param socksDto {@link SocksDto} instance from user's request.
     */
    @Transactional
    @Override
    public void addSocks(SocksDto socksDto) throws WrongColorException {
        logger.info("Was invoked 'addSocks' method from {}", SocksStorageImpl.class.getSimpleName());
        Optional<SocksEntity> stockOptional = socksRepository
                .findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart());
        SocksEntity newSocksStock;
        if (socksDto.getColor()==null) {
            throw new WrongColorException();
        }
        if (stockOptional.isPresent()) {
            newSocksStock = stockOptional.get();
            newSocksStock.setQuantity(newSocksStock.getQuantity() + socksDto.getQuantity());
        } else {
            newSocksStock = socksMapper.toEntity(socksDto);
        }
        socksRepository.save(newSocksStock);
    }

    /**
     * Remove desired quantity of socks from DB.
     * @param socksDto {@link SocksEntity} instance from user's request.
     * @throws SocksPairNotFound if socks not present
     */
    @Override
    public void removeSocks(SocksDto socksDto) throws SocksPairNotFound {
        logger.info("Was invoked 'removeSocks' method from {}", SocksStorageImpl.class.getSimpleName());
        SocksEntity socks = socksRepository.findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart())
                .orElseThrow();
        if (socks.getQuantity() < socksDto.getQuantity()) {
            throw new SocksPairNotFound();
        }
        socks.setQuantity(socks.getQuantity() - socksDto.getQuantity());
        socksRepository.save(socks);
    }

}
