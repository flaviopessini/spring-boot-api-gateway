package com.flaviopessini.springbootapigateway.dtos.v1;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"userName", "password"})
public class AccountCredentialsDTO implements Serializable {

    private String userName;

    private String password;
}
