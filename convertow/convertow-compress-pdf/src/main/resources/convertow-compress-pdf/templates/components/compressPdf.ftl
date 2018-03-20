[#assign restPath = ctx.contextPath + "/.rest/compresspdf/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "pdf";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = "Compressed.pdf";
</script>