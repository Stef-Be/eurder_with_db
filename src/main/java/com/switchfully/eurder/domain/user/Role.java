package com.switchfully.eurder.domain.user;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.eurder.domain.user.Feature.ADD_ITEM;
import static com.switchfully.eurder.domain.user.Feature.ORDER_ITEMS;

public enum Role {
    ADMIN(newArrayList(ADD_ITEM)), CUSTOMER(newArrayList(ORDER_ITEMS));

    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
