[#assign threeCols = content.threeCols!false]

<div class="col-md-6 [#if threeCols]col-lg-4 col-xl-4 [#else]col-lg-3 col-xl-3 [/#if] wow animated fadeInUp" data-wow-delay=".2s">
    <div class="about-single">
        <i class="material-icons mdi ${content.materialIcon!}"></i>
        <h2 class="subtitle">${content.title!}</h2>
        ${cmsfn.decode(content).text!}
    </div>
</div>