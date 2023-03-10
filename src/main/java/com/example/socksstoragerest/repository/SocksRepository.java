package com.example.socksstoragerest.repository;

import com.example.socksstoragerest.entity.SocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Long> {
    Optional<SocksEntity> findByColorAndCottonPart(String color, int cottonPart);
    Optional<SocksEntity> findSocksEntityByColor(String color);
    Collection<SocksEntity> findByColorAndCottonPartAfter(String color, int cottonPart);
    Collection<SocksEntity> findByColorAndCottonPartBefore(String color, int cottonPart);



}
