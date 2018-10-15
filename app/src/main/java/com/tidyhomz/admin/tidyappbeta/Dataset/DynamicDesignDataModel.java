package com.tidyhomz.admin.tidyappbeta.Dataset;

import org.json.JSONArray;

/**
 * Created by Admin on 8/1/2017.
 */

public class DynamicDesignDataModel {
    private int module_id;
    private int status;
    private String ModuleName;
    private JSONArray ModuleData;

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public JSONArray getModuleData() {
        return ModuleData;
    }

    public void setModuleData(JSONArray moduleData) {
        ModuleData = moduleData;
    }
}
