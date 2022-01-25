package by.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.demo.domain.LimitRangeEntity;

@Repository
public interface LimitRangeRepository extends JpaRepository<LimitRangeEntity, UUID> {

}
