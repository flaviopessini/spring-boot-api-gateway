package com.flaviopessini.springbootapigateway.dtos.v1;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"userName", "authenticated", "created", "expiration", "accessToken", "refreshToken"})
public class TokenDTO implements Serializable {

    private String userName;

    private boolean authenticated;

    private Date created;

    private Date expiration;

    private String accessToken;

    private String refreshToken;
}
