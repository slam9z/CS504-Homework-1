package demo.rest;

import demo.domain.RunningInformation;
import demo.service.RunningInformationAnalysisService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by KozuePC on 4/12/2017.
 */
@RestController
@RequestMapping("/api/runningInformations")
public class RunningInformationBulkUploadController {

    private final String kDefaultPage = "0";
    private final String kDefaultSizePerPage = "2";
    private final String kDefaultSortDir = "DESC";
    private final String kDefaultSortBy = "heartRate";

    private final String kFieldRunningId = "runningId";
    private final String kFieldTotalRunning = "totalRunning";
    private final String kFieldHeartRate = "heartRate";
    private final String kFieldUserId = "userId";
    private final String kFieldUserName = "userName";
    private final String kFieldUserAddress = "userAddress";
    private final String kFieldHealthWarningLevel = "healthWarningLevel";

    @Autowired
    private RunningInformationAnalysisService informationAnalysisService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Validated @RequestBody List<RunningInformation> runningInformations){
        this.informationAnalysisService.saveRunningInformation(runningInformations);
    }

    @RequestMapping(value = "/update/{Id}", method = RequestMethod.PUT)
    public RunningInformation update(@PathVariable Long Id, @Validated @RequestBody RunningInformation runningInformations){
        RunningInformation info = this.informationAnalysisService.findById(Id);

        if(info != null){
            info.setRunningId(runningInformations.getRunningId());
            info.setLatitude(runningInformations.getLatitude());
            info.setLongitude(runningInformations.getLongitude());
            info.setRunningDistance(runningInformations.getRunningDistance());
            info.setTotalRunningTime(runningInformations.getTotalRunningTime());
            return info;
        }
        return null;
    }

    @RequestMapping(value="/id/{Id}",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findById(@PathVariable Long Id) {
       RunningInformation info = this.informationAnalysisService.findById(Id);
       JSONObject json_result = JsonCustomContent(info);
       return new ResponseEntity(json_result, HttpStatus.OK);
    }

    @RequestMapping(value="/deleteByRunningId/{runningId}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String runningId){
        this.informationAnalysisService.deleteByRunningInformationRunningId(runningId);
    }

    @RequestMapping(value = "/purge",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void purge(){
        this.informationAnalysisService.deleteAll();
    }

    @RequestMapping(value="/listedBy",method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findAllByOrderBy(
            @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = kDefaultSizePerPage) Integer size,
            @RequestParam(value = "sortBy", required = false, defaultValue = kDefaultSortBy)  String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = kDefaultSortDir)  Sort.Direction sortDir){

        //Retrieve all results that match the searching parameter(s)
        Page<RunningInformation> rawResults = this.informationAnalysisService.findAllByOrderByHealthWarningLevelDesc(
                new PageRequest(page,size, sortDir, sortBy));

        //only getting the content of the response
        List<RunningInformation> content = rawResults.getContent();

        List<JSONObject> results = JsonListCustomHelper(content);
        return new ResponseEntity<List<JSONObject>>(results, HttpStatus.OK);
    }

    //helper function for customize a single RunningInformation Object
    private JSONObject JsonCustomContent(RunningInformation info) {
        if (info == null) return null;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(kFieldRunningId, info.getRunningId());
        jsonObj.put(kFieldTotalRunning, info.getTotalRunningTime());
        jsonObj.put(kFieldHeartRate, info.getHeartRate());
        jsonObj.put(kFieldUserId, info.getId());
        jsonObj.put(kFieldUserName, info.getUserInfo().getUsername());
        jsonObj.put(kFieldUserAddress, info.getUserInfo().getAddress());
        jsonObj.put(kFieldHealthWarningLevel, info.getHealthWarningLevel());
        return jsonObj;
    }

    private List<JSONObject> JsonListCustomHelper(List<RunningInformation> content){
        List<JSONObject> results = new LinkedList<JSONObject>();
        for(RunningInformation info : content){
            JSONObject jsonObj = JsonCustomContent(info);
            results.add(jsonObj);
        }
        return results;
    }

}
