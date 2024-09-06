package com.csmtech.SJTA.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDataDto {

	private Short menuId;
    private String menuName;
    private String menuurl;

    public MenuDataDto(Short menuId, String menuName,String menuurl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuurl=menuurl;
    }
}
