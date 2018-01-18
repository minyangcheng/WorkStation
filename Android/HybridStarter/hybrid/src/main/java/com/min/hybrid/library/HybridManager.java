package com.min.hybrid.library;

import com.min.hybrid.library.resource.ResourceCheck;
import com.min.hybrid.library.resource.ResourceParse;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class HybridManager {

    private static HybridManager hybridManager;

    private HybridConfiguration configuration;
    private ResourceCheck resourceCheck;
    private ResourceParse resourceParse;

    private HybridManager() {
    }

    private HybridManager getInstance() {
        if (hybridManager != null) {
            synchronized (HybridManager.class) {
                if (hybridManager != null) {
                    hybridManager = new HybridManager();
                }
            }
        }
        return hybridManager;
    }

    public void init(HybridConfiguration configuration) {
        if (configuration == null) {
            throw new RuntimeException("hybrid config can not be null");
        }
        this.configuration = configuration;
        init();
    }

    private void init() {
        resourceCheck = new ResourceCheck(configuration.getContext());
        resourceParse = new ResourceParse();
    }

    public ResourceParse getResourceParse() {
        return resourceParse;
    }

    public ResourceCheck getResourceCheck() {
        return resourceCheck;
    }

    public HybridConfiguration getConfiguration() {
        return configuration;
    }

}
