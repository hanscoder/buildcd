package com.givval.tobedeleted.version;

import java.util.Date;

public class VersionBasedOnDate {

    private final int major;

    private final int minor;

    private final int buildnumber;

    public VersionBasedOnDate(SplittedDate aDate) {
        if (aDate == null) {
            throw new IllegalArgumentException("Provide a valid data, null is not allowed.");
        }

        this.major = aDate.year();
        this.minor = aDate.month() + 1;
        this.buildnumber = 0;
    }

    public VersionBasedOnDate(int major, int minor,  int buildnumber) {
        if (major < 0) {
            throw new IllegalArgumentException("Provide a positive major version.");
        }
        this.major = major;

        if (minor < 1 || minor > 12) {
            throw new IllegalArgumentException("Provide a minor version which is between 1 and 12.");
        }
        this.minor = minor;

        if (buildnumber < 0) {
            throw new IllegalArgumentException("Provide a positive build number.");
        }
        this.buildnumber = buildnumber;
    }

    public int major() {
        return this.major;
    }

    public int minor() {
        return this.minor;
    }

    public int buildnumber() {
        return this.buildnumber;
    }

    public VersionBasedOnDate increaseBuildnumber() {
        return new VersionBasedOnDate(this.major, this.minor, this.buildnumber + 1);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionBasedOnDate)) return false;

        VersionBasedOnDate that = (VersionBasedOnDate) o;

        if (buildnumber != that.buildnumber) return false;
        if (major != that.major) return false;
        if (minor != that.minor) return false;

        return true;
    }

    @Override
    public final int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + buildnumber;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", this.major, this.minor, this.buildnumber);
    }
}
