[#assign restPath = ctx.contextPath + "/.rest/jpgtobmp/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "jpg";
    var restPath = "${restPath!}";
    var zipFile = false;
    var singleFile = true;
    var resultExtension = ".bmp";
</script>
