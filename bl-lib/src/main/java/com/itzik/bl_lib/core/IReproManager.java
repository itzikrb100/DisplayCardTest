package com.itzik.bl_lib.core;

public interface IReproManager {

    void init(CardReproCallback cardReproCallback);

    void requestWithRetry(int limit);

    void stopRequest();

    void release();

}
