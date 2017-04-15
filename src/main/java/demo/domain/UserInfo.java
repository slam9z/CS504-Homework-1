package demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by KozuePC on 4/12/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@Data

public class UserInfo {

    @JsonIgnore
    private Long id;

    @NotNull(message = "error.general.notnull")
    private final String username;

    @NotNull(message = "error.general.notnull")
    private final String address;

    public UserInfo(){
        this.username = "";
        this.address = "";
    }

    public UserInfo(
       Long id,
       @JsonProperty("username") String username,
       @JsonProperty("address")String address
    ){
        this.id = id;
        this.username = username;
        this.address = address;
    }



}
