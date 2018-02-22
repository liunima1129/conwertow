package com.convertow;

/**
 * This class is optional and represents the configuration for the convertow-core module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/convertow-core</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class ConvertOwCore {
    /* you can optionally implement info.magnolia.module.ModuleLifecycle */
    public String path;
    public String fileDelimiter;

    public ConvertOwCore(){
        path = getPath();
        fileDelimiter = getFileDelimiter();
    }

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
