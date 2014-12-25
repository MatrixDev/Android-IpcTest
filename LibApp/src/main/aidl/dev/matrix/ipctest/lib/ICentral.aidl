package dev.matrix.ipctest.lib;

import dev.matrix.ipctest.lib.ICallback;

interface ICentral {
    int getRequestsCount();
    void sendRandomRequest(long userTime, String inputArg, ICallback callback);
}
