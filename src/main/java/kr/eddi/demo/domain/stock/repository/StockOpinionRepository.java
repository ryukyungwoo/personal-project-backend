package kr.eddi.demo.domain.stock.repository;

import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.entity.StockOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockOpinionRepository extends JpaRepository<StockOpinion, String> {

    @Query("SELECT so FROM StockOpinion so JOIN FETCH so.stock s WHERE s.ticker = :ticker")
    Optional<StockOpinion> findByTicker(@Param("ticker") String ticker);

}
