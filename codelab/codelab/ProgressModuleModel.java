package com.example.codelab;

public class ProgressModuleModel {
    private String moduleName;
    private int progress;
    private boolean isCompleted;

    public ProgressModuleModel(String moduleName, int progress, boolean isCompleted) {
        this.moduleName = moduleName;
        this.progress = progress;
        this.isCompleted = isCompleted;
    }

    public String getModuleName() { return moduleName; }
    public int getProgress() { return progress; }
    public boolean isCompleted() { return isCompleted; }
}
