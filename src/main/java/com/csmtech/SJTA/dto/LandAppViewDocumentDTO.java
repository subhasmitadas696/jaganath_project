package com.csmtech.SJTA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandAppViewDocumentDTO {

	String documentName;
	String docsPath;
    Short pendingAtRoleid;

}
