[#assign restPath = ctx.contextPath + "/.rest/rotatepdf/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<div class="container rotate-form">
    <form class="shake" role="form" method="post" id="rotatePDF" name="contact-form" data-toggle="validator" novalidate="true">
        <select class="custom-select" id="degrees">
            <option selected>${i18n['rotation.degrees']}</option>
            <option value="90">90</option>
            <option value="180">180</option>
            <option value="270">270</option>
        </select>
        <div class="form-submit mt-4">
            <button class="btn btn-common dropify-convert">${i18n['rotate']}</button>
            <div id="msgSubmit" class="h3 text-center hidden"></div>
            <div class="clearfix"></div>
        </div>
    </form>
</div>

<script>
    var fileSupported = "pdf";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = "Rotated.pdf";
</script>