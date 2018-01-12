[#macro assignLinkParameters item field]

    [#assign linkType = item[field]!]

    [#if linkType=="internal"]
        [#include "linkInternal.ftl"]
    [#elseif linkType=="external"]
        [#include "linkExternal.ftl"]
    [#elseif linkType=="download"]
        [#include "linkDownload.ftl"]
    [#elseif linkType=="email"]
        [#include "linkEmail.ftl"]
    [/#if]

[/#macro]
