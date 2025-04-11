package com.p1nero.dote.entity.ai.epicfight.api;

public interface IMultiPhaseEntityPatch {
    int getPhase();
    void setPhase(int phase);

    default boolean checkPhase(int min, int max){
        return getPhase() >= min && getPhase() <= max;
    }

}
