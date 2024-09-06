package com.csmtech.SJTA.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleMenuDataDTO {
	
	    private Short  moduleId;
	    private String moduleName;
	    private List<MenuDataDto> menus;
	    
	    public void addMenu(MenuDataDto menuData) {
	        this.menus.add(menuData);
	    }
	    
	    public ModuleMenuDataDTO(Short  moduleId, String moduleName) {
	        this.moduleId = moduleId;
	        this.moduleName = moduleName;
	        this.menus = new ArrayList<>();
	    }

}
