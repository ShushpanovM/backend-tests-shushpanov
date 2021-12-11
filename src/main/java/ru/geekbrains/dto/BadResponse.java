
package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BadResponse implements Serializable
{

    @JsonProperty("status")
    public Integer status;
    @JsonProperty("success")
    public Boolean success;
    @JsonProperty("data")
    public Data data;


}
