package com.example.tasks

/**
 * Created by krishna_hotha on 3/29/16.
 */
class ProjectVersion{

    Integer major
    Integer minor
    Boolean release=false

    ProjectVersion(Integer major, Integer minor, Boolean release) {
        this.major = major
        this.minor = minor
        this.release = release
    }

    ProjectVersion(Integer major, Integer minor) {
        this.major = major
        this.minor = minor
    }


    @Override
    public String toString() {

        "$major.$minor${release ? '' : '-SNAPSHOT'}"
    }
}