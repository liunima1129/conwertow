package com.convertow.definition;

/**
 * Created by Miroslav on 20.12.2017.
 */
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miroslav Pashaliski on 3/7/2017.
 */
public class NavigationAreaDefinition extends ConfiguredAreaDefinition {

    private List<UserLinksResolver> userLinksResolvers = new ArrayList<>();

    public List<UserLinksResolver> getUserLinksResolvers() {
        return userLinksResolvers;
    }

    public void setUserLinksResolvers(List<UserLinksResolver> userLinksResolvers) {
        this.userLinksResolvers = userLinksResolvers;
    }
}
