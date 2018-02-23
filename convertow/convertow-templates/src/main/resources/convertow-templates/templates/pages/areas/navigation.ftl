<header id="header">
    <nav class="navbar navbar-expand-lg scrolling-navbar nav-bg">
        <div class="container">

            <div class="navbar-header">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-navbar" aria-controls="main-navbar" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="mdi mdi-menu"></span>
                    <span class="mdi mdi-menu"></span>
                    <span class="mdi mdi-menu"></span>
                </button>
                <a class="navbar-brand" href="index.html"><img src="${ctx.contextPath}/.resources/convertow-templates/webresources/images/logo.gif" alt=""></a>
            </div>
            <div class="collapse navbar-collapse" id="main-navbar">
                <ul class="navbar-nav mr-auto w-100 justify-content-end">
                    [#assign node = cmsfn.nodeById("9943f909-ace6-4f85-8743-5147edfca0a0",  "category")]
                    [#assign pageNode = cmsfn.nodeById("da327d1d-374d-4d38-8846-814c46b7fdfc", "website")]
                    [#assign wrappedNode = corefn.getI18nWrapper(node!)]
                    [#assign wrappedPageNode = corefn.getI18nWrapper(pageNode!)]
                    [#assign categories = cmsfn.children(wrappedNode!, "mgnl:category")]
                    [#assign pages = cmsfn.children(wrappedPageNode!, "mgnl:page")]
                    [#list categories as category]
                        [#assign categoryDetails = cmsfn.asContentMap(category!)!]
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${categoryDetails.displayName!}
                            </a>
                            <div class="dropdown-menu">
                                [#list pages as page]
                                    [#assign pageDetails = cmsfn.asContentMap(page!)!]
                                    [#if pageDetails.categories?has_content]
                                        [#if pageDetails.categories?seq_contains(categoryDetails.@uuid)]
                                            <a class="dropdown-item" href="${cmsfn.link(page!)}">${pageDetails.navigationTitle!}</a>
                                        [/#if]
                                    [/#if]
                                [/#list]
                            </div>
                        </li>
                    [/#list]
                </ul>

                <div class="search-icon">
                    <span class="open-search">
                    <i class="mdi mdi-magnify btn btn-common"></i>
                    </span>
                </div>
                <form role="search" class="navbar-form">
                    <div class="container">
                        <div class="row">
                            <div class="form-group has-feedback">
                                <input type="text" placeholder="${i18n['search.placeholder']}" class="form-control">
                                <div class="close"><i class="mdi mdi-close"></i></div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>

        <ul class="wpb-mobile-menu">

            [#list categories as category]
                [#assign categoryDetails = cmsfn.asContentMap(category!)!]
                <li>
                    <a class="active" href="#">
                    ${categoryDetails.displayName!}
                    </a>
                    <ul class="dropdown">
                        [#list pages as page]
                            [#assign pageDetails = cmsfn.asContentMap(page!)!]
                            [#if pageDetails.categories?has_content]
                                [#if pageDetails.categories?seq_contains(categoryDetails.@uuid)]
                                    <li>
                                        <a class="active" href="${cmsfn.link(page!)}">${pageDetails.navigationTitle!}</a>
                                    </li>
                                [/#if]
                            [/#if]
                        [/#list]
                    </ul>
                </li>
            [/#list]

        </ul>

    </nav>
</header>