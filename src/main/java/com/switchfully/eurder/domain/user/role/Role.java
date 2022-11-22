package com.switchfully.eurder.domain.user.role;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.eurder.domain.user.role.Feature.*;

public enum Role {
    ADMIN(newArrayList(CRUD_ITEMS, VIEW_CUSTOMERS)), CUSTOMER(newArrayList(ORDER_ITEMS));

    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
