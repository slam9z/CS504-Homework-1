package demo.service.impl;

import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInformationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by KozuePC on 4/12/2017.
 */
@Service
public class RunningInformationAnalysisServiceImpl implements RunningInformationAnalysisService {

    RunningInformationRepository runningInformationRepository;

    @Autowired
    public RunningInformationAnalysisServiceImpl(RunningInformationRepository runningInformationRepository) {
        this.runningInformationRepository = runningInformationRepository;
    }

    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformations) {
        return this.runningInformationRepository.save(runningInformations);
    }

    @Override
    public Page<RunningInformation> findByHeartRate(int heartRate, Pageable pageable) {
        return this.runningInformationRepository.findByHeartRate(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable) {
        return this.runningInformationRepository.findByHeartRateGreaterThan(heartRate, pageable);
    }

    @Override
    public void deleteAll() {
        this.runningInformationRepository.deleteAll();
    }

    @Override
    public void deleteByRunningInformationRunningId(String runningId) {
        List<RunningInformation> runningInfos = this.runningInformationRepository.findByRunningId(runningId);
        runningInfos.forEach(item ->  this.runningInformationRepository.delete(item));

    }

    @Override
    public Page<RunningInformation> findAllByOrderByHealthWarningLevelDesc(Pageable pageable) {
        return  this.runningInformationRepository.findAllByOrderByHealthWarningLevelDesc(pageable);
    }

    public RunningInformation findById(Long id ){
        return this.runningInformationRepository.findOne(id);
    }


}
