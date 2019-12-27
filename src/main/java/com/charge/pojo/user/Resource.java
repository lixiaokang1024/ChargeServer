package com.charge.pojo.user;

public class Resource {
    private Integer id;

    private String menuKey;

    private String menuName;

    private String parentMenuKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentMenuKey() {
        return parentMenuKey;
    }

    public void setParentMenuKey(String parentMenuKey) {
        this.parentMenuKey = parentMenuKey;
    }
}