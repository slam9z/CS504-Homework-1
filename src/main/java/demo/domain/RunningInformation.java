package demo.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Random;

/**
 * Created by KozuePC on 4/12/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Data
@Table(name = "private")
public class RunningInformation {
    public enum HealthWarningLevel{
        LOW, NORMAL, HIGH;

        public static HealthWarningLevel getHealthWarningLevel(int heartRate){
            HealthWarningLevel healthWarningLevel = LOW;
            if (heartRate > 120){
                healthWarningLevel = HIGH;
            }else if (heartRate > 75){
                healthWarningLevel = NORMAL;
            }else{}
            return healthWarningLevel;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private final UserInfo userInfo;

    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}", message = "error.RunningInformation.runningId.format")
    private String runningId;

    private double latitude;
    private double longitude;

    @Min(value = 0, message = "error.RunningInformation.positive")
    private double runningDistance;
    @Min(value = 0,message = "error.RunningInformation.positive")
    private double totalRunningTime;

    @JsonIgnore
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonIgnore))
    private int heartRate;

    @JsonIgnore
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonIgnore))
    private HealthWarningLevel healthWarningLevel;

    @JsonIgnore
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonIgnore))
    private Date timestamp = null;

    public RunningInformation(){
        this.userInfo = null;
    }

    public RunningInformation(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    public RunningInformation(final String username, final String address){
        this.userInfo = new UserInfo(this.id,username, address);
    }


    public String getUsername() {
        return this.userInfo == null ? null : this.userInfo.getUsername();
    }

    public String getAddress() {
        return this.userInfo == null ? null : this.userInfo.getAddress();
    }

    @JsonCreator
    public RunningInformation(
        @JsonProperty("runningId") String runningId,
        @JsonProperty("userInfo") UserInfo userInfo,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude,
        @JsonProperty("runningDistance") String runningDistance,
        @JsonProperty("totalRunningTime") String totalRunningTime
        ) {

        this.runningId = runningId;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.userInfo = userInfo;
        this.runningDistance = Double.parseDouble(runningDistance);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.heartRate = getRandomHeartRate(60, 200);
        this.healthWarningLevel = HealthWarningLevel.getHealthWarningLevel(this.heartRate);
        this.timestamp = new Date();
    }

    private int getRandomHeartRate(int min, int max){
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }


}
