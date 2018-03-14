[#assign restPath = ctx.contextPath + "/.rest/jpgtopdf/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "jpg";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = ".pdf";
</script>