package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by KozuePC on 4/12/2017.
 */
public interface RunningInformationAnalysisService {

    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformations);

    public Page<RunningInformation> findByHeartRate(int heartRate, Pageable pageable);

    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable);

    public RunningInformation findById(Long id);

    public void deleteAll();

    public void deleteByRunningInformationRunningId(String runningId);

    Page<RunningInformation> findAllByOrderByHealthWarningLevelDesc(Pageable pageable);


}
