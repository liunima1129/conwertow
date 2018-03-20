[#assign restPath = ctx.contextPath + "/.rest/bmptopng/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "bmp";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = ".png";
</script>