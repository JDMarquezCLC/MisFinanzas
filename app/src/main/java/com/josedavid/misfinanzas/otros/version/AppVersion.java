package com.josedavid.misfinanzas.otros.version;

public class AppVersion {
    private int release;
    private int majorupdate;
    private int minorupdate;

    public AppVersion() {
        this.release = 1;
        this.majorupdate = 1;
        this.minorupdate = 0;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getMajorupdate() {
        return majorupdate;
    }

    public void setMajorupdate(int majorupdate) {
        this.majorupdate = majorupdate;
    }

    public int getMinorupdate() {
        return minorupdate;
    }

    public void setMinorupdate(int minorupdate) {
        this.minorupdate = minorupdate;
    }

    @Override
    public String toString() {
        return release + "." + majorupdate + "." + minorupdate;
    }
}
