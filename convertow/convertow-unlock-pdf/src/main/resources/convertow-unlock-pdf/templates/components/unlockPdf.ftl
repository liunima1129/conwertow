[#assign restPath = ctx.contextPath + "/.rest/unlockpdf/convert?id="/]

<section class="wrapper">
    <div class="container">
        <div class="row">
            <div class="col-md-12 ">
            [#include "/convertow-templates/templates/components/includes/errorUnlockPdf.ftl"]
            </div>
        </div>
    </div>
</section>

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<div class="container protect-form">
    <form class="shake" role="form" method="post" name="contact-form" data-toggle="validator" novalidate="true">
        <div class="form-group label-floating is-empty">
            <label class="control-label" for="password">${i18n['password']}</label>
            <input class="form-control" id="password" type="password" name="password" required="" data-error="${i18n['password.error']}">
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-submit mt-4">
            <button class="btn btn-common dropify-convert">${i18n['unlock']}</button>
            <div id="msgSubmit" class="h3 text-center hidden"></div>
            <div class="clearfix"></div>
        </div>
    </form>
</div>
<script>
    var fileSupported = "pdf";
    var restPath = "${restPath!}";
</script>

