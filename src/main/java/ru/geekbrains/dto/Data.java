
package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class Data implements Serializable
{

    @JsonProperty("error")
    public String error;
    @JsonProperty("request")
    public String request;
    @JsonProperty("method")
    public String method;


}
