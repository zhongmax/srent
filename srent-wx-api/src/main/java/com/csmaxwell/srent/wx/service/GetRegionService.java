package com.csmaxwell.srent.wx.service;

import com.csmaxwell.srent.db.domain.SrentRegion;
import com.csmaxwell.srent.db.service.SrentRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRegionService {

    @Autowired
    private SrentRegionService regionService;

    private static List<SrentRegion> srentRegions;

    protected List<SrentRegion> getSrentRegions() {
        if (srentRegions == null) {
            createRegion();
        }
        return srentRegions;
    }

    private synchronized void createRegion() {
        if (srentRegions == null) {
            srentRegions = regionService.getAll();
        }
    }
}
