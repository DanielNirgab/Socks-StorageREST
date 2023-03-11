package com.example.socksstoragerest.service;


import com.example.socksstoragerest.constant.OperationEnum;
import com.example.socksstoragerest.dto.SocksDto;
import com.example.socksstoragerest.entity.SocksEntity;
import com.example.socksstoragerest.repository.SocksRepository;
import com.example.socksstoragerest.service.impl.SocksStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocksStorageImplTest {
    @Mock
    private SocksRepository socksRepository;
    @InjectMocks
    private SocksStorageImpl out;

    private SocksEntity testSocks;
    private SocksDto testDto;

    @BeforeEach
    void init() {
        testSocks = new SocksEntity();
        testSocks.setCottonPart(50);
        testSocks.setQuantity(1);
        testSocks.setColor("Black");

        testDto = new SocksDto();
        testDto.setColor(testSocks.getColor());
        testDto.setQuantity(testSocks.getQuantity());
        testDto.setCottonPart(testSocks.getCottonPart());
    }

    @Test
    void shouldReturnZeroForEachOperationType_whenGetQuantityOfSocksAndSocksNotFound() {
        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.empty());
        when(socksRepository.findByColorAndCottonPartAfter(anyString(), anyInt())).thenReturn(Collections.emptyList());
        when(socksRepository.findByColorAndCottonPartBefore(anyString(), anyInt())).thenReturn(Collections.emptyList());

        int resultEqual = out.getQuantityOfSocks("black", OperationEnum.equal, 0);
        int resultMoreThan = out.getQuantityOfSocks("black", OperationEnum.moreThan, 0);
        int resultLessThan = out.getQuantityOfSocks("black", OperationEnum.lessThan, 0);

        assertThat(resultEqual).isEqualTo(0);
        assertThat(resultMoreThan).isEqualTo(0);
        assertThat(resultLessThan).isEqualTo(0);
    }

    @Test
    void shouldReturnIntegerForEachOperationType_whenGetQuantityOfSocksAndSocksFound() {
        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));
        when(socksRepository.findByColorAndCottonPartAfter(anyString(), anyInt())).thenReturn(List.of(testSocks));
        when(socksRepository.findByColorAndCottonPartBefore(anyString(), anyInt())).thenReturn(List.of(testSocks));

        int resultEqual = out.getQuantityOfSocks("black", OperationEnum.equal, 0);
        int resultMoreThan = out.getQuantityOfSocks("black", OperationEnum.moreThan, 0);
        int resultLessThan = out.getQuantityOfSocks("black", OperationEnum.lessThan, 0);

        assertThat(resultEqual).isEqualTo(testSocks.getQuantity());
        assertThat(resultMoreThan).isEqualTo(testSocks.getQuantity());
        assertThat(resultLessThan).isEqualTo(testSocks.getQuantity());
    }


    @Test
    void shouldAddQuantityAndSaveSocksOnce_whenAddSocksWithPresentCombination() {
        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));
        out.addSocks(testDto);
        verify(socksRepository, times(1)).save(testSocks);
    }

    @Test
    void shouldDecreaseQuantityAndSaveSocksOnce_whenRemoveSocksWithPresentCombination() {
        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));
        assertThatNoException().isThrownBy(() -> out.removeSocks(testDto));
        verify(socksRepository, times(1)).save(testSocks);
    }

}
