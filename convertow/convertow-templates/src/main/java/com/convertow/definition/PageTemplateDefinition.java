package com.convertow.definition;

/**
 * Created by Miroslav on 20.12.2017.
 */
import info.magnolia.module.site.ResourceDefinition;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ConfiguredTemplateDefinition} for pages.
 */
public class PageTemplateDefinition extends ConfiguredTemplateDefinition {

    /**
     * Using the <code>jsFiles</code> of this {@link ConfiguredTemplateDefinition}
     * to be rendered at the bottom of the page.
     */
    private List<ResourceDefinition> jsFiles = new ArrayList<>();

    @Inject
    public PageTemplateDefinition(TemplateAvailability templateAvailability) {
        super(templateAvailability);
    }

    public PageTemplateDefinition() {
        super(Components.getComponent(TemplateAvailability.class));
    }

    public List<ResourceDefinition> getJsFiles() {
        return jsFiles;
    }

    public void setJsFiles(List<ResourceDefinition> jsFiles) {
        this.jsFiles = jsFiles;
    }

}

