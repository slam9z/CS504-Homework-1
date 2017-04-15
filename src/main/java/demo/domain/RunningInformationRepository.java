package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by KozuePC on 4/12/2017.
 */
@Repository
public interface RunningInformationRepository extends JpaRepository<RunningInformation, Long> {

    Page<RunningInformation> findByHeartRateGreaterThan(
            @Param("heartRate") int heartRate,
            Pageable pageable
    );

    Page<RunningInformation> findByHeartRate(
            @Param("heartRate") int heartRate,
            Pageable pageable
    );

    Page<RunningInformation> findAllByOrderByHealthWarningLevelDesc(
            Pageable pageable
    );

    List<RunningInformation> findByRunningId(
            @Param("runningId") String runningId
    );
}
