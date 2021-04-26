package rsstoolkit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rsstoolkit.model.TopicModel;

@Repository
public interface TopicRepository extends JpaRepository<TopicModel, Integer> {

    @Query(value = "SELECT a.KEYWORD, a.CNT, b.NEWS_HEADER, b.NEWS_LINK FROM(Select TOP 3 keyword, COUNT(*) as CNT FROM TOPIC where analyse_id = ?1 "
            + "group by keyword order by CNT DESC, KEYWORD ASC) a "
            + "Left join (SELECT keyword, news_header, news_link FROM TOPIC where analyse_id = ?1) b "
            + "ON a.keyword = b.keyword", nativeQuery = true)
    List<Map<String, Object>> findAnalysisById(Integer id);

}