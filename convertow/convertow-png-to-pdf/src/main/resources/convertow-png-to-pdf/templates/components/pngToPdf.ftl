[#assign restPath = ctx.contextPath + "/.rest/pngtopdf/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "png";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = ".pdf";
</script>