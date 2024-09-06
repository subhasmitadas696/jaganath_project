package com.csmtech.SJTA.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author prasanta.sethi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDetailsDTO {
    private BigInteger queryId;
    private String query;
    private String name;
    private String mobileno;
//	public String status;
}
