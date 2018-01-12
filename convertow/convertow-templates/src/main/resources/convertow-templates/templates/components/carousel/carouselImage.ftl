
[#assign image = damfn.getAssetLink(content.image!)!]
[#assign text = cmsfn.decode(content).text!/]
[#assign linkId = content.page!/]
[#assign link = cmsfn.link("website", linkId)!/]

<div class="carousel-item [#if ctx.index == "0"]active[/#if]">
    <img class="d-block w-100" src="${image!}" alt="${content.title!}">
    <div class="carousel-caption d-md-block">
        [#if content.title?has_content]
            <h1 class="animated wow fadeInDown hero-heading" data-wow-delay=".6s">${content.title!}</h1>
        [/#if]
        [#if text?has_content]
            <h5 class="animated fadeInUp wow hero-sub-heading" data-wow-delay=".8s">Ready to Use for Business, Agency, Startup, Portfolio Sites and More...</h5>
        [/#if]

        <a href="javascript:void(0)" class="animated fadeInUp wow btn btn-common" data-wow-delay=".8s"><i class="material-icons mdi mdi-arrow-collapse-down"></i> Download Now</a>
    </div>
</div>
