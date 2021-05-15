package com.logical.code.study.zoned;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ZoneEntityRepository extends JpaRepository<ZoneEntity, Long> {
}
