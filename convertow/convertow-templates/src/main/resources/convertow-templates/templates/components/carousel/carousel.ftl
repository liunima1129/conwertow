[#if (components?size>0) || cmsfn.editMode]
    [#if cmsfn.editMode]
    <div cms:add="bar"></div>
    [/#if]

<div id="main-slide" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        [#list components as component ]
            <li data-target="#main-slide" data-slide-to="1" class="[#if component_index == 0]active[/#if]"></li>
        [/#list]
    </ol>

    <div class="carousel-inner">
    [#list components as component ]
        [@cms.component content=component contextAttributes={"index":"${component_index!}"}  /]
    [/#list]
    </div>

    <a class="carousel-control-prev" href="#main-slide" role="button" data-slide="prev">
        <span class="carousel-control" aria-hidden="true"><i class="mdi mdi-arrow-left" data-ripple-color="#F0F0F0"></i></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#main-slide" role="button" data-slide="next">
        <span class="carousel-control" aria-hidden="true"><i class="mdi mdi-arrow-right" data-ripple-color="#F0F0F0"></i></span>
        <span class="sr-only">Next</span>
    </a>

</div>
[/#if]
