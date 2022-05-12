package com.miladalemansour.api.repository;

public enum LockStatus {
    LOCK(1),
    UNLOCK(0);
    private int status;
    LockStatus(int status){
        this.status = status;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

}
