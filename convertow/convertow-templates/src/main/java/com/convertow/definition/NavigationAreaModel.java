package com.convertow.definition;

import info.magnolia.jcr.predicate.NodeTypePredicate;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.AreaDefinition;
import info.magnolia.rendering.template.type.DefaultTemplateTypes;
import info.magnolia.templating.functions.TemplatingFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by Miroslav Pashaliski on 3/7/2017.
 */
public class NavigationAreaModel extends RenderingModelImpl<AreaDefinition> {

    private static final Logger log = LoggerFactory.getLogger(NavigationAreaModel.class);

    private static final String CURRENT_ACTIVE_CSS_CLASS = "active";
    private static final String CHILD_ACTIVE_CSS_CLASS = "child-active";
    private static final String DEMO_ABOUT_TEMPLATE_SUBTYPE = "demo-about";

    private final TemplatingFunctions templatingFunctions;

    @Inject
    public NavigationAreaModel(Node content, AreaDefinition definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
        super(content, definition, parent);

        this.templatingFunctions = templatingFunctions;
    }

    public List<NavigationItem> getRootPages() {
        try {
            final Node home = templatingFunctions.parentWithTemplateType(content, DefaultTemplateTypes.HOME);
            return getChildPages(home);
        } catch (RepositoryException e) {
            log.error("Could not retrieve pages for navigation.", e);
        }
        return Collections.emptyList();
    }

    public List<NavigationItem> getChildPages() {
        try {
            int depth = content.getDepth();

            if (depth == 2) {
                return getChildPages(content);
            } else if (depth > 2) {
                Node parent = content;
                while (depth > 2) {
                    parent = parent.getParent();
                    depth--;
                }
                return getChildPages(parent);
            }
        } catch (RepositoryException e) {
            log.error("Could not retrieve pages for navigation.", e);
        }
        return Collections.emptyList();
    }

    public String getAboutDemoLink() {
        Node siteRoot = templatingFunctions.siteRoot(content);
        String link = null;
        try {
            List<Node> nodes = templatingFunctions.contentListByTemplateType(siteRoot, DefaultTemplateTypes.FEATURE, DEMO_ABOUT_TEMPLATE_SUBTYPE);
            if (nodes.size() > 0) {
                link = templatingFunctions.link(nodes.get(0));
            }
        } catch (RepositoryException e) {
            log.error("Could not get the '{}' page.", DEMO_ABOUT_TEMPLATE_SUBTYPE, e);
        }
        return link;
    }

    private List<NavigationItem> getChildPages(Node parent) throws RepositoryException {
        final List<NavigationItem> navigationItems = new LinkedList();

        final Iterator<Node> nodeIterator = NodeUtil.getNodes(parent, NodeTypes.Page.NAME).iterator();
        while (nodeIterator.hasNext()) {
            final Node pageNode = nodeIterator.next();
            final boolean hide = PropertyUtil.getBoolean(pageNode, NavigationItem.PROPERTY_NAME_HIDE_PAGE, false);
            if (!hide) {
                try {
                    final NavigationItem navigationItem = getNavigationItem(pageNode);
                    navigationItems.add(navigationItem);
                } catch (RepositoryException e) {
                    log.error("Could not create page object from node.", e);
                }
            }
        }
        return navigationItems;
    }

    private NavigationItem getNavigationItem(Node node) throws RepositoryException {
        final NavigationItem navigationItem = new NavigationItem();
        String title = PropertyUtil.getString(node, NavigationItem.PROPERTY_NAME_NAVIGATION_TITLE);
        if (title == null) {
            title = PropertyUtil.getString(node, NavigationItem.PROPERTY_NAME_TITLE, node.getName());
        }

        navigationItem.setName(title);
        if (isActive(node)) {
            navigationItem.setCssClass(CURRENT_ACTIVE_CSS_CLASS);
        } else if (isChildActive(node)) {
            navigationItem.setCssClass(CHILD_ACTIVE_CSS_CLASS);
        }
        navigationItem.setLink(templatingFunctions.link(node));
        return navigationItem;
    }

    private boolean isChildActive(Node node) throws RepositoryException {
        final Iterator<Node> nodeIterator = NodeUtil.collectAllChildren(node, new NodeTypePredicate(NodeTypes.Page.NAME)).iterator();
        while (nodeIterator.hasNext()) {
            final Node child = nodeIterator.next();
            if (content.getPath().equals(child.getPath())) {
                return true;
            }
        }
        return false;
    }

    private boolean isActive(Node pageNode) throws RepositoryException {
        return pageNode.getPath().equals(content.getPath());
    }

    /**
     * Simple Pojo for navigation items.
     */
    public class NavigationItem {

        public static final String PROPERTY_NAME_HIDE_PAGE = "hideInNav";
        public static final String PROPERTY_NAME_TITLE = "title";
        public static final String PROPERTY_NAME_NAVIGATION_TITLE = "navigationTitle";

        private String name;
        private String link;
        private String cssClass;

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setCssClass(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getCssClass() {
            return cssClass;
        }
    }
}
