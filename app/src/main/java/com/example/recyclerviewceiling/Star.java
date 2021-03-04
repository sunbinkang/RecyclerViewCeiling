package com.example.recyclerviewceiling;

/**
 * Date: 2021/2/21
 * Author: SunBinKang
 * Description:
 */
public class Star {

    private String name;

    private String groupName;

    public Star(String name, String groupName) {
        this.name = name;
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
