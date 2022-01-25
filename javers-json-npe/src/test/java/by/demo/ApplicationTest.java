package by.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import by.demo.domain.LimitEntity;
import by.demo.domain.LimitRangeEntity;
import by.demo.domain.LimitSignatureEntity;
import by.demo.repository.LimitRangeRepository;
import by.demo.repository.LimitRepository;
import by.demo.repository.LimitSignatureRepository;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ApplicationTest {

  private final LimitRepository limitRepository;
  private final LimitRangeRepository limitRangeRepository;
  private final LimitSignatureRepository limitSignatureRepository;
  private final Javers javers;

  private static Set<LimitEntity> getLimits() {
    return IntStream.range(1, 3)
        .mapToObj(i -> LimitEntity.builder()
            .caption("LimitEntity " + i)
            .build())
        .map(limitEntity -> {
          limitEntity.setLimitRanges(genLimitRanges(limitEntity));
          return limitEntity;
        })
        .collect(Collectors.toSet());
  }

  private static Set<LimitRangeEntity> genLimitRanges(LimitEntity limitEntity) {
    return IntStream.range(1, 3)
        .mapToObj(i -> LimitRangeEntity.builder()
            .limit(limitEntity)
            .caption("LimitRangeEntity " + i)
            .build())
        .map(limitRangeEntity -> {
          limitRangeEntity.setLimitSignatures(genLimitSignatures(limitRangeEntity));
          return limitRangeEntity;
        })
        .collect(Collectors.toSet());
  }

  private static Set<LimitSignatureEntity> genLimitSignatures(LimitRangeEntity limitRangeEntity) {
    return IntStream.range(1, 3)
        .mapToObj(i -> LimitSignatureEntity.builder()
            .limitRange(limitRangeEntity)
            .caption("LimitSignatureEntity " + i)
            .build())
        .collect(Collectors.toSet());
  }

  @Test
  void should_test_npe() {
    limitRepository.saveAll(getLimits());
    assertEquals(2, limitRepository.count());
    assertEquals(4, limitRangeRepository.count());
    assertEquals(8, limitSignatureRepository.count());

    var limitEntity = limitRepository.findAll()
        .get(0);
    var limitRangeEntity = limitEntity.getLimitRanges()
        .iterator()
        .next();
    var limitSignatureEntity = limitRangeEntity.getLimitSignatures()
        .iterator()
        .next();

    //1 Change sub-sub child LimitSignatureEntity
    limitSignatureEntity.setCaption("1 Change");
    limitRepository.save(limitEntity);

    //2 Change sub child LimitRangeEntity
    limitRangeEntity.setCaption("2 Change");
    limitRepository.save(limitEntity);

    //3 Change parent LimitEntity
    limitEntity.setCaption("3 Change");
    limitRepository.save(limitEntity);

    //4 Change all
    limitSignatureEntity.setCaption("4 Change");
    limitRangeEntity.setCaption("4 Change");
    limitEntity.setCaption("4 Change");
    limitRepository.save(limitEntity);

    List<Shadow<LimitEntity>> shadowList = findHistory(limitEntity);
    assertEquals(3, shadowList.size());
  }

  private List<Shadow<LimitEntity>> findHistory(LimitEntity limitEntity) {
    var queryBuilder = QueryBuilder.byInstanceId(limitEntity.getId(), LimitEntity.class)
        //.withChildValueObjects()
        //.withScopeDeepPlus()
        //.withScopeCommitDeep()
        .build();
    List<Shadow<LimitEntity>> shadowLimits = javers.findShadows(queryBuilder);
    return shadowLimits;
  }
}