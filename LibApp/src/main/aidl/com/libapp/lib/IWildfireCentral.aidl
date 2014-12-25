package com.libapp.lib;

import com.libapp.lib.ICallback;

interface IWildfireCentral {
    int getRequestsCount();
    void sendRandomRequest(long userTime, String inputArg, ICallback callback);
}
