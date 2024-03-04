package com.example.project1.scrap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ScrapRepository {
    private final EntityManager em;

    // 스크랩
    public ScrapResponse.DetailDTO findScrap(int notice_id, int session_user_id) {
        String q = """
                SELECT
                    id,
                    CASE
                        WHEN user_id IS NULL THEN FALSE
                        ELSE TRUE
                    END AS isScrap,
                    (SELECT COUNT(*) FROM scrap_tb WHERE notice_id = ?) AS scrapCount
                FROM
                    scrap_tb
                WHERE
                    notice_id = ? AND user_id = ?;
                """;

        Query query = em.createNamedQuery(q);
        query.setParameter(1, notice_id);
        query.setParameter(2, notice_id);
        query.setParameter(3, session_user_id);

        Object[] row = (Object[]) query.getSingleResult();
        Integer id = (Integer) row[0];
        Boolean isScrap = (Boolean) row[1];
        Long scrapCount = (Long) row[2];

        System.out.println("id : " + id);
        System.out.println("isLove : " + isScrap);
        System.out.println("scrapCount : " + scrapCount);

        ScrapResponse.DetailDTO responseDTO = new ScrapResponse.DetailDTO(id, isScrap, scrapCount);

        return responseDTO;
    }

    // 부분 조회
    public void findById(){

    }

    // 전체 조회
    public void findAll(){

    }

    // 저장
    @Transactional
    public void save(){

    }

    // 삭제
    @Transactional
    public void deleteById(){

    }

    // 수정
    @Transactional
    public void update(){

    }
}
