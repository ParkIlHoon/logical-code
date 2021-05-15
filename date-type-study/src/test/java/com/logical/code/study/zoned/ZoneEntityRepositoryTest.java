package com.logical.code.study.zoned;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZoneEntityRepositoryTest {
    @Autowired
    ZoneEntityRepository zoneEntityRepository;

    @Test
    void 로그필드_세팅_확인() {
        // given
        ZoneEntity entity = new ZoneEntity();
        entity.setName("name");

        // when
        ZoneEntity savedEntity = zoneEntityRepository.save(entity);

        // then
        assertNotNull(savedEntity.getCreateDate());
        assertNotNull(savedEntity.getUpdateDate());

        System.out.println(savedEntity.getCreateDate().toString());
        System.out.println(savedEntity.getUpdateDate().toString());
    }

    @Test
    void 수정일자_갱신_확인() {
        // given
        ZoneEntity entity = new ZoneEntity();
        entity.setName("name");

        // when
        ZoneEntity savedEntity = zoneEntityRepository.save(entity);
        savedEntity.setName("updated");
        ZoneEntity updatedEntity = zoneEntityRepository.save(savedEntity);

        // then
        assertNotNull(updatedEntity.getCreateDate());
        assertNotNull(updatedEntity.getUpdateDate());
        assertNotEquals(savedEntity.getUpdateDate(), updatedEntity.getUpdateDate());

        System.out.println(updatedEntity.getCreateDate().toString());
        System.out.println(updatedEntity.getUpdateDate().toString());
    }
}