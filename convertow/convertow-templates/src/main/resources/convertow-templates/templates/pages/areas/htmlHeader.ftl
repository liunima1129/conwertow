[#-------------- ASSIGNMENTS --------------]
[#-- Page's model & definition, based on the rendering hierarchy and not the node hierarchy --]
[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]
[#assign pageDef = ctx.pageDef!]

[#-------------- RENDERING --------------]
<title>${content.windowTitle!content.title!}</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="${content.description!""}" />
<meta name="keywords" content="${content.keywords!""}" />
<meta name="author" content="Miroslav Pashaliski" />
<meta name="generator" content="Powered by ConvertOW - Free Online Convertor" />
<link rel="icon" href="${ctx.contextPath}/.resources/convertow-templates/webresources/images/favicon.ico" type="image/x-icon" />

<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<![endif]-->

<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/materialdesignicons.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/material.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/ripples.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/magnific-popup.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/settings.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/slicknav.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/animate.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/responsive.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/color-switcher.css" />
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/.resources/convertow-templates/webresources/css/colors/blue.css" />